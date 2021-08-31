package com.jamespark_h.bmicalculationactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.jamespark_h.bmicalculationactivity.databinding.ActivityMainBinding
import com.jamespark_h.bmicalculationactivity.databinding.ActivityResultBmiBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnBMICal.setOnClickListener {
            // 값이 비어있는지 체크
            if (binding.editHeight.text.isEmpty() || binding.editWeight.text.isEmpty()) {
                Toast.makeText(applicationContext,
                    "키와 몸무게를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val height : Int = binding.editHeight.text.toString().toInt()
            val weight : Int = binding.editWeight.text.toString().toInt()

            var intent = Intent(this, ResultAcitivity::class.java)
            intent.putExtra("Height", height)
            intent.putExtra("Weight", weight)


            startActivity(intent)
        }
    }
}