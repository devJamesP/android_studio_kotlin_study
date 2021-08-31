package com.jamespark_h.bmicalculationactivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jamespark_h.bmicalculationactivity.databinding.ActivityResultBmiBinding
import kotlin.math.pow

class ResultAcitivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBmiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // viewBinding
        binding = ActivityResultBmiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var height = intent.getIntExtra("Height", 0 )
        val weight = intent.getIntExtra("Weight", 0 )

        // 나누기 0 방지
        if (height == 0) {
            height = -1
        }

        val resultBMI =  (weight / (height / 100.0).pow(2.0))
        val resultRank = when{
            resultBMI >= 35.0 -> "고도 비만"
            resultBMI >= 30.0 -> "중정도 비만"
            resultBMI >= 25.0 -> "경도 비만"
            resultBMI >= 23.0 -> "과체중"
            resultBMI >= 18.5 -> "정상 체중"
            else -> "저체중"
        }

        binding.txResultBMI.text = resultBMI.toString()
        binding.txResultRank.text = resultRank

    }
}