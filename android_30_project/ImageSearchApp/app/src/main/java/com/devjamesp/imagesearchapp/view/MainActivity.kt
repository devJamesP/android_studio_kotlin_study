package com.devjamesp.imagesearchapp.view

import android.Manifest
import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.devjamesp.imagesearchapp.adapter.PhotoAdapter
import com.devjamesp.imagesearchapp.data.Repository
import com.devjamesp.imagesearchapp.data.models.PhotoModel
import com.devjamesp.imagesearchapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val scope = MainScope()
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initData()
        initBindViews()

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) requestWriteStoragePermission()
        else fetchRandomPhotos()
    }

    private fun requestWriteStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            WRITE_EXTERNAL_STORAGE_RQCODE_PERMISION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val writeExternalStoragePermissionGranted =
            requestCode == WRITE_EXTERNAL_STORAGE_RQCODE_PERMISION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED

        if (writeExternalStoragePermissionGranted) fetchRandomPhotos()
    }


    private fun initData() {
        binding.imageListRecycerView.apply {
            adapter = PhotoAdapter()
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initBindViews() {
        binding.imageSearchEditText
            .setOnEditorActionListener { editText, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    currentFocus?.let { view ->
                        val inputMethodManager =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)

                        view.clearFocus()
                    }
                }
                fetchRandomPhotos(editText.text.toString())
                true
            }

        binding.refreshLayout.setOnRefreshListener {
            fetchRandomPhotos(binding.imageSearchEditText.text.toString())
        }

        (binding.imageListRecycerView.adapter as? PhotoAdapter)?.setOnClickPhoto { photo ->
            showDownloadPhotoConfirmationDialog(photo)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun fetchRandomPhotos(query: String? = null) = scope.launch {
        try {
            Repository.getRandomPhotos(query)?.let { photos ->
                binding.errorDescriptionTextView.isVisible = false
                (binding.imageListRecycerView.adapter as? PhotoAdapter)?.apply {
                    setPhotoList(photos)
                    notifyDataSetChanged()
                }
            }
            binding.imageListRecycerView.visibility = View.VISIBLE
        } catch (e: Exception) {
            binding.imageListRecycerView.visibility = View.INVISIBLE
            binding.errorDescriptionTextView.isVisible = true
        } finally {
            binding.shimmerLayout.isVisible = false
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun showDownloadPhotoConfirmationDialog(photo: PhotoModel) {
        AlertDialog.Builder(this@MainActivity)
            .setMessage("이 사진을 저장하시겠습니까?")
            .setPositiveButton("저장") { dialog, _ ->
                dialog.dismiss()
                downloadPhoto(photo.urls?.full)
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun downloadPhoto(photoUrl: String?) {
        photoUrl ?: return

        Snackbar.make(binding.root, "다운로드 중...", Snackbar.LENGTH_INDEFINITE).show()

        Glide.with(this)
            .asBitmap()
            .load(photoUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(
                object: CustomTarget<Bitmap>(SIZE_ORIGINAL, SIZE_ORIGINAL) {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?,
                    ) {
                        saveBitmapToMediaStore(resource)

                        val wallPaperManager = WallpaperManager.getInstance(this@MainActivity)
                        val snackbar = Snackbar.make(
                            binding.root,
                            "다운로드 완료",
                            Snackbar.LENGTH_SHORT
                        )


                        if (wallPaperManager.isWallpaperSupported
                            && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && wallPaperManager.isSetWallpaperAllowed) {
                            snackbar.setAction("배경 화면으로 저장") { view ->
                                try {
                                    wallPaperManager.setBitmap(resource)
                                } catch (e : Exception) {
                                    Snackbar.make(binding.root, "배경화면 저장 실패", Snackbar.LENGTH_SHORT).show()
                                }
                            }
                            snackbar.duration = Snackbar.LENGTH_INDEFINITE
                        }
                        snackbar.show()
                    }

                    override fun onLoadStarted(placeholder: Drawable?) {
                        super.onLoadStarted(placeholder)
                        Snackbar.make(binding.root, "다운로드 중...", Snackbar.LENGTH_INDEFINITE).show()
                    }
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        Snackbar.make(binding.root, "다운로드 실패", Snackbar.LENGTH_SHORT).show()
                    }
                    override fun onLoadCleared(placeholder: Drawable?) = Unit
                }
            )
    }

    private fun saveBitmapToMediaStore(bitmap: Bitmap) {
        val fileName = "${System.currentTimeMillis()}.jpg"
        val resolver = applicationContext.contentResolver
        val imageCollectionUri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }
        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val imageUri = resolver.insert(imageCollectionUri, imageDetails)
        imageUri ?: return

        resolver.openOutputStream(imageUri).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imageDetails.clear()
            imageDetails.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(imageUri, imageDetails, null, null)
        }
    }

    companion object {
        private const val WRITE_EXTERNAL_STORAGE_RQCODE_PERMISION = 1111
    }
}