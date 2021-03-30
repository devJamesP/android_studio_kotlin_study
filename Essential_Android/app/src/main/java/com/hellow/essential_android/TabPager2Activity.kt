package com.hellow.essential_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class TabPager2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_pager2)

        // tabLayout 변수 생성
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout2).apply {
            addTab(newTab().setText("ONE"))
            addTab(newTab().setText("TWO"))
            addTab(newTab().setText("THREE"))
        }

        // viewPager 변수 생성 및 어댑터 설정
        val viewPager = findViewById<ViewPager>(R.id.view_pager2)
        val adapter = ThreePageAdapter(LayoutInflater.from(this@TabPager2Activity))
        viewPager.adapter = adapter

        // tabLayout과 viewPager를 연동
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }
        })

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }
}

class ThreePageAdapter(
    val layoutInflater: LayoutInflater
) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        when(position) {
            0 -> {
                val view = layoutInflater.inflate(R.layout.fragment_one, container, false)
                container.addView(view)
                return view
            }
            1 -> {
                val view = layoutInflater.inflate(R.layout.fragment_two, container, false)
                container.addView(view)
                return view
            }
            2 -> {
                val view = layoutInflater.inflate(R.layout.fragment_three, container, false)
                container.addView(view)
                return view
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.fragment_one, container, false)
                container.addView(view)
                return view
            }
        }
    }

    // 여기서 `object`는 instantiateItem의 return view에 해당함
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return 3
    }

    // 여기서 `object`는 instantiateItem의 return view에 해당함
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }
}