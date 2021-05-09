package com.devJamesP.audiorecoderactivity

import android.annotation.SuppressLint
import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CountUpView(
    context: Context,
    attrs: AttributeSet? = null
): AppCompatTextView(context, attrs) {

    private var startTimeStamp: Long = 0L

    private val countUpActions: Runnable = object: Runnable{
        override fun run() {
            val currentTimeStamp = SystemClock.elapsedRealtime()

            val countTimeSeconds = ((currentTimeStamp - startTimeStamp)/1000L).toInt()
            updateCountTime(countTimeSeconds)

            handler?.postDelayed(this, 1000L)
        }
    }
    fun startCountUp() {
        startTimeStamp = SystemClock.elapsedRealtime()
        handler?.post(countUpActions)
    }

    fun stopCountUp() {
        handler?.removeCallbacks(countUpActions)
    }

    fun clearCountTime() {
        updateCountTime(0)
    }
    @SuppressLint("SetTextI18n")
    private fun updateCountTime(countTimeSecondes: Int) {
        val minutes = countTimeSecondes / 60
        val seconds = countTimeSecondes % 60

        text = "%02d:%02d".format(minutes, seconds)
    }
}