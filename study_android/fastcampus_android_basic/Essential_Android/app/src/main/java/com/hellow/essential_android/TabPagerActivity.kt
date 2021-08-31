package com.hellow.essential_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class TabPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_pager)

        // 원하는 만큼 tab_layout을 더해줌
        val tab_Layout : TabLayout = findViewById<TabLayout>(R.id.tab_layout).apply {
            addTab(newTab().setText("ONE"))
            addTab(newTab().setText("TWO"))
            addTab(newTab().setText("THREE"))
        }

        // viewPager, pagerAdapter생성
        val view_Pager : ViewPager = findViewById<ViewPager>(R.id.view_pager)

        val pagerAdapter = FragmentPagerAdapter(supportFragmentManager, 3)
        view_Pager.adapter = pagerAdapter

        // tabLayout과 viewPager를 연동
        tab_Layout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                view_Pager.currentItem = tab!!.position
            }
        })

        // 페이저가 이동했을 때 탭을 이동시키는 코드
        view_Pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_Layout))
    }
}

class FragmentPagerAdapter (
    fragmentManager: FragmentManager,
    val tabCount: Int
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0-> {
                return Fragment1()
            }
            1-> {
                return Fragment2()
            }
            2-> {
                return Fragment3()
            }
            else -> {
                return Fragment1()
            }
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}
