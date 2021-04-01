package com.hellow.essential_android

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        // 권한 획득 변수
        val cameraPermissionCheck = ContextCompat.checkSelfPermission(
            this@PermissionActivity,
            android.Manifest.permission.CAMERA
        )
        PackageManager.PERMISSION_GRANTED

        val btnPermission : Button = findViewById<Button>(R.id.btnPermission).apply {
            setOnClickListener {
                if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {
                    // 권한이 없는 경우
                    ActivityCompat.requestPermissions(
                        this@PermissionActivity,
                        arrayOf(android.Manifest.permission.CAMERA),
                        1000
                    )
                } else {
                    // 권한이 있는 경우
                    Log.d("permissions", "권한이 이미 있음")
                }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 승낙
                Log.d("permissions", "승낙")
            } else {
                // 거부
                Log.d("permissions", "거부")
            }
        }
    }

}