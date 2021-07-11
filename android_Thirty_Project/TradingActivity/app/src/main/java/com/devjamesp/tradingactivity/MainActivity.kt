package com.devjamesp.tradingactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.devjamesp.tradingactivity.chatlist.ChatListFragment
import com.devjamesp.tradingactivity.home.HomeFragment
import com.devjamesp.tradingactivity.mypage.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val chatListFragment = ChatListFragment()
        val myPageFragment = MyPageFragment()

        replaceFragment(homeFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menu ->
            when(menu.itemId) {
                R.id.item_home -> replaceFragment(homeFragment)
                R.id.item_chatList -> replaceFragment(chatListFragment)
                R.id.item_myPage -> replaceFragment(myPageFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment) {
        supportFragmentManager.commit {
            addToBackStack("...")
            replace(R.id.fragmentContainerView, fragment)
        }
    }
}