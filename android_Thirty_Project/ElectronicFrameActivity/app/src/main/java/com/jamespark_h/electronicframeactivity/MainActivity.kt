package com.jamespark_h.electronicframeactivity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private val addPhotoButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.addPhotoButton)
    }

    private val startPhotoFrameModeButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.startPhotoFrameModeButton)
    }

    private val imageViewList: List<ImageView> by lazy {
        mutableListOf<ImageView>().apply {
            add(findViewById<ImageView>(R.id.imgViewPhoto11))
            add(findViewById<ImageView>(R.id.imgViewPhoto12))
            add(findViewById<ImageView>(R.id.imgViewPhoto13))
            add(findViewById<ImageView>(R.id.imgViewPhoto21))
            add(findViewById<ImageView>(R.id.imgViewPhoto22))
            add(findViewById<ImageView>(R.id.imgViewPhoto23))
        }
    }

    private val imageUriList: MutableList<Uri> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init Button
        initAddPhotoButton()
        initStartPhotoFrameModeButton()
    }

    private fun initAddPhotoButton() {
        addPhotoButton.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // 앱에 이미 권한이 부여되었는지 확인
                    // todo 권한이 잘 부여되었을 떄 갤러리에서 사진을 선택하는 기능 작성
                    navigatePhotos()
                }
                shouldShowRequestPermissionRationale(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) -> {
                    // 교육용 팝업에서 앱에 권한이 필요한 이유 설명
                    // todo 교육용 팝업 확인 후 권한 팝업을 띄우는 기능 작성
                    showPermissionContextPopup()

                }
                else -> {
                    // 직접적으로 권한 여부를 사용자에게 요청
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        RQ_READ_MEMORY
                    )
                }
            }

        }
    }


    // 요청 수락 시
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        when (requestCode) {
            RQ_READ_MEMORY -> {
                if (grantResults.isNotEmpty() && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    // todo 권한이 부여된 경우 동작 수행
                    // 사진 이미지 탐색
                    navigatePhotos()

                } else {
                    Toast.makeText(applicationContext, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {

            }
        }
    }

    private fun navigatePhotos() {
        // 안드로이드 내부 정의된 액티비티를 실행
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, RQ_PHOTO_NAVIGATION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 예외처리 ::
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode){
            RQ_PHOTO_NAVIGATION -> {
                val selectedImageUri: Uri? = data?.data

                if (selectedImageUri != null) {
                    if (imageUriList.size == 6) {
                        Toast.makeText(this, "사진이 꽉 찼습니다..", Toast.LENGTH_SHORT).show()
                        return
                    }
                    // 이미지 로드 성공
                    imageUriList.add(selectedImageUri)
                    imageViewList[imageUriList.size - 1].setImageURI(selectedImageUri)
                } else {
                    // 실패
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }

            }
            else -> {
                // RQ코드 오류처리 :: 예외처리
                Toast.makeText(applicationContext, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요")
            .setMessage("전자액자에 앱에서 사진을 불러오기 위해 권한이 필요합니다")
            .setPositiveButton("동의하기") { _, _ ->
                // 동의 후 사용자에게 권한 여부
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    RQ_READ_MEMORY
                )
            }
            .setNegativeButton("취소하기") { _, _ ->

            }
            .create()
            .show()
    }

    private fun initStartPhotoFrameModeButton() {
        startPhotoFrameModeButton.setOnClickListener{
            val intent = Intent(this, PhotoFrameActivity::class.java)
            imageUriList.forEachIndexed { index, uri ->
                intent.putExtra("photo$index", uri.toString())
            }
            intent.putExtra("photoListSize", imageUriList.size)
            startActivity(intent)
        }
    }



    companion object {
        const val RQ_READ_MEMORY = 1000
        const val RQ_PHOTO_NAVIGATION = 2000
        const val TAG = "MainActivity"

    }
}