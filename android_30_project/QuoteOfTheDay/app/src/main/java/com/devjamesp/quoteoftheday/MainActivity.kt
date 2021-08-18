package com.devjamesp.quoteoftheday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {
    private val viewPager: ViewPager2 by lazy {
        findViewById(R.id.viewPager)
    }

    private val progressBar : ProgressBar by lazy {
        findViewById(R.id.progress)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initData()
    }

    private fun initViews() {
         viewPager.setPageTransformer { page, position ->
             when {
                 position.absoluteValue >= 1f -> {
                     page.alpha = 0f
                 }
                 position == 0f -> {
                     page.alpha = 1f
                 }
                 else -> {
                     page.alpha = 1f - 2f * position.absoluteValue
                 }
             }
        }
    }

    private fun initData() {
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setConfigSettingsAsync(
            remoteConfigSettings {
                minimumFetchIntervalInSeconds = 60
            }
        )
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            progressBar.visibility = View.GONE
            if (it.isSuccessful) {
                val quotes = parseQutoesJson(remoteConfig.getString("quotes"))
                val isNameRevealed = remoteConfig.getBoolean("is_name_revealed")

                displayQuotesPager(quotes, isNameRevealed)
            }
        }
    }

    private fun parseQutoesJson(json: String): List<Quote> {
        val jsonArray = JSONArray(json)
        var jsonList = emptyList<JSONObject>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            jsonObject?.let {
                jsonList = jsonList + it
            }
        }
        return jsonList.map {
            Quote(
                it.getString("quote"),
                it.getString("name")
            )
        }

    }

    private fun displayQuotesPager(quotes: List<Quote>, isNameRevealed: Boolean) {
        viewPager.apply {
            adapter = QuotesPagerAdapter(
               quotes =  quotes,
                isNameRevealed = isNameRevealed
            )
            var rootFocusPostionDisplay = adapter!!.itemCount / 2 - 3
            setCurrentItem(rootFocusPostionDisplay, false)
        }
    }
}