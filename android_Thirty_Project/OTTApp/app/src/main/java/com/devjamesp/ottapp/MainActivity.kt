package com.devjamesp.ottapp

import android.app.Activity
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devjamesp.ottapp.databinding.ActivityMainBinding
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var isGateringMotionAnimating: Boolean = false
    private var isCuratingMotionAnimating: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        makeStatusBarTransParent()

        initAppBar()

        initInsetMargin()

        initScrollViewListeners()

        initMotionLayoutListeners()
    }

    private fun initScrollViewListeners() {
        binding.scrollView.smoothScrollTo(0, 0)

        binding.scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrolledValue = binding.scrollView.scrollY

            if (binding.scrollView.scrollY > 150f.dpToPx(this).toInt()) {
                // 스크롤이 150px초과로 내려갔을 때
                if (isGateringMotionAnimating.not()) {
                    binding.gatheringDigitalThingsMotionLayout.transitionToEnd()
                    binding.gatheringDigitalThingsBackgroundMotionLayout.transitionToEnd()
                    binding.buttonShownMotionLayout.transitionToEnd()
                }
            } else {
                // 아직 내려가기 전
                if (isGateringMotionAnimating.not()) {
                    binding.gatheringDigitalThingsMotionLayout.transitionToStart()
                    binding.gatheringDigitalThingsBackgroundMotionLayout.transitionToStart()
                    binding.buttonShownMotionLayout.transitionToStart()
                }
            }

            if (scrolledValue > binding.scrollView.height) {
                if (isCuratingMotionAnimating.not()) {
                    binding.curationAnimationMotionLayout.setTransition(R.id.curation_animation_start1, R.id.curation_animation_end1)
                    binding.curationAnimationMotionLayout.transitionToEnd()
                }
            }
        }
    }

    private fun initMotionLayoutListeners() {
        binding.gatheringDigitalThingsMotionLayout.setTransitionListener(object :
            MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                isGateringMotionAnimating = true
            }
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                isGateringMotionAnimating = false
            }
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
        })

        binding.curationAnimationMotionLayout.setTransitionListener(object :
            MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                isCuratingMotionAnimating = true
            }
            override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) {
                when (currentId) {
                    R.id.curation_animation_end1 -> {
                        binding.curationAnimationMotionLayout.setTransition(R.id.curation_animation_start2, R.id.curation_animation_end2)
                        binding.curationAnimationMotionLayout.transitionToEnd()
                    }
                }
            }
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
        })
    }

    // statusBar영역과 actionbar영역이 겹쳐서 statusBar영역 밑의 영역으로 둔거임.
    private fun initInsetMargin() = with(binding) {
        ViewCompat.setOnApplyWindowInsetsListener(coordinator) { v: View, insets: WindowInsetsCompat ->
            val params = v.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = insets.systemWindowInsetBottom
            // toolbar의 topmargin을 줬음.
            toolbarContainer.layoutParams =
                (toolbarContainer.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    setMargins(0, insets.systemWindowInsetTop, 0, 0)
                }
            collapsingToolbarContainer.layoutParams =
                (collapsingToolbarContainer.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    setMargins(0, 0, 0, 0)
                }
            insets.consumeSystemWindowInsets()
        }
    }

    // 일정 수치만큼 수직 스크롤을 하게되면 알파값을 줘서 appbar를 불투명하게 줌.
    private fun initAppBar() {
        /*
        appbar의 영역은 높이가 420dp
        topPadding = 300px
        readAlphaScrollHeight = 420 - 스크롤한 영역의 높이
        abstractOffset = 수직 방향 offset의 절댓값
        readAlphaVerticalOffset = 오프셋 - 300이 음수면 0, 300이상 스크롤을 하게되면 차이만큼 저장
        percentage = 300스크롤 이상부터 offset / (120..최대 스크롤 음수값)
        alpha 계산 :: 300전까지는 0, 이후부터는 1 - (1 - 2*percentage) or 1
         */
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val topPadding = 300f.dpToPx(this)
            val realAlphaScrollHeight = appBarLayout.measuredHeight - appBarLayout.totalScrollRange
            val abstractOffset = abs(verticalOffset)

            val realAlphaVerticalOffset = if (abstractOffset - topPadding < 0) 0f else abstractOffset - topPadding

            if (abstractOffset < topPadding) {
                binding.toolbarBackgroundView.alpha = 0f
                return@OnOffsetChangedListener
            }

            val percentage = realAlphaVerticalOffset / realAlphaScrollHeight
            binding.toolbarBackgroundView.alpha =
                1 - (if (1 - 2*percentage < 0) 0f else 1 - 2*percentage)
        })

        initActionBar()
    }

    private fun initActionBar() = with(binding) {
        toolbar.navigationIcon = null
        toolbar.setContentInsetsAbsolute(0, 0)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setHomeButtonEnabled(false)
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowHomeEnabled(false)
        }
    }
}



private fun Float.dpToPx(context: Context): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)

// statusbar 투명화
private fun Activity.makeStatusBarTransParent() {
    window.apply {
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = Color.TRANSPARENT
    }
}