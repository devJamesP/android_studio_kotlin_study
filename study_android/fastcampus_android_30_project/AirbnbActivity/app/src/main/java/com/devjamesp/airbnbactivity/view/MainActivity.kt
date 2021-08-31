package com.devjamesp.airbnbactivity.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.devjamesp.airbnbactivity.R
import com.devjamesp.airbnbactivity.api.HouseService
import com.devjamesp.airbnbactivity.model.HouseDTO
import com.devjamesp.airbnbactivity.model.HouseModel
import com.devjamesp.airbnbactivity.view.adapter.HouseListAdapter
import com.devjamesp.airbnbactivity.view.adapter.HouseViewPagerAdapter
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import com.naver.maps.map.widget.LocationButtonView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnMapReadyCallback, Overlay.OnClickListener {
    private lateinit var naverMap : NaverMap
    private lateinit var locationSource : FusedLocationSource
    private val infoWindow : InfoWindow = InfoWindow()

    private val mapView : MapView by lazy {
        findViewById(R.id.mapView)
    }

    private val viewPager : ViewPager2 by lazy {
        findViewById(R.id.houseViewPager)
    }

    private val recyclerView : RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private val viewPagerAdapter = HouseViewPagerAdapter(
        itemClicked = {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "[지금 이 가격에 예약하세요.!!] ${it.title} ${it.price} 사진보기 : ${it.imgUrl}")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, ""))
        }
    )
    private val recyclerViewAdapter = HouseListAdapter()

    private val currentLocationButtonView : LocationButtonView by lazy {
        findViewById(R.id.currentLocationButton)
    }

    private val bottomSheetTitleTextView : TextView by lazy {
        findViewById(R.id.bottomSheetTitleTextView)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)

        viewPager.adapter = viewPagerAdapter

        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 페이지 스와이프 시 발생하는 콜백 함수들 중 페이지 선택 콜백 함수
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val selectedHouseModel = viewPagerAdapter.currentList[position]
                val cameraUpdate = CameraUpdate.scrollTo(LatLng(selectedHouseModel.lat, selectedHouseModel.lng))
                    .animate(CameraAnimation.Easing, 1000L)
                naverMap.moveCamera(cameraUpdate)

                // 뷰페이지 이동 시 마커에 정보창 표시
                updateInfoWindow(selectedHouseModel)
            }
        })
    }

    private fun updateInfoWindow(house: HouseModel) {
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this@MainActivity) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return house.title
            }
        }
        infoWindow.close()
        infoWindow.position = LatLng(house.lat, house.lng)
        infoWindow.open(naverMap)
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map

        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.500493, 127.029470))
        naverMap.moveCamera(cameraUpdate)

        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false

        currentLocationButtonView.map = naverMap

        locationSource = FusedLocationSource(this@MainActivity, LOCATION_PERMISION_REQUEST_CODE)
        naverMap.locationSource = locationSource

        getHouseListFromAPI()
    }

    private fun getHouseListFromAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(HouseService::class.java).also {
            it.getHouseList()
                .enqueue(object: Callback<HouseDTO>{
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<HouseDTO>, response: Response<HouseDTO>) {
                        if (response.isSuccessful.not()) {
                            Toast.makeText(this@MainActivity, "데이터를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            response.body()?.let { dto ->
                                updateMarker(dto.items)

                                // List UI Update
                                viewPagerAdapter.submitList(dto.items)
                                recyclerViewAdapter.submitList(dto.items)

                                // bottomSheet title update
                                bottomSheetTitleTextView.text = "${dto.items.size}개의 숙소"
                            }
                        }
                    }

                    override fun onFailure(call: Call<HouseDTO>, t: Throwable) {
                       Toast.makeText(this@MainActivity, "데이터를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }



    private fun updateMarker(houses: List<HouseModel>) {
        houses.forEach { house ->
            val marker = Marker()
            marker.position = LatLng(house.lat, house.lng)
            marker.onClickListener = this

            marker.apply {
                map = naverMap
                tag = house.id
                icon = MarkerIcons.BLACK
                iconTintColor = Color.RED
            }
        }
    }

    override fun onClick(overlay: Overlay): Boolean {
        val selectedModel = viewPagerAdapter.currentList.firstOrNull { it.id == overlay.tag }
        selectedModel?.let {
            val position = viewPagerAdapter.currentList.indexOf(it)
            viewPager.currentItem = position
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISION_REQUEST_CODE) { return
        } else {
            if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
                if (!locationSource.isActivated) {
                    naverMap.locationTrackingMode  = LocationTrackingMode.None
                    Toast.makeText(this, "위치를 추적하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


    companion object {
        private const val LOCATION_PERMISION_REQUEST_CODE = 1000
    }

}