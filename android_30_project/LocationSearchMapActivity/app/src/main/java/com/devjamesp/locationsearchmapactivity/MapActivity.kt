package com.devjamesp.locationsearchmapactivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.devjamesp.locationsearchmapactivity.databinding.ActivityMapBinding
import com.devjamesp.locationsearchmapactivity.model.LocationLatLngEntity
import com.devjamesp.locationsearchmapactivity.model.SearchResultEntity
import com.devjamesp.locationsearchmapactivity.response.address.AddressInfo
import com.devjamesp.locationsearchmapactivity.response.address.AddressInfoResponse
import com.devjamesp.locationsearchmapactivity.utility.RetrofitUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MapActivity : AppCompatActivity(), OnMapReadyCallback, CoroutineScope {
    private lateinit var binding: ActivityMapBinding

    private lateinit var mMap: GoogleMap
    private var currentMarker: Marker? = null

    private lateinit var searchResult: SearchResultEntity

    private lateinit var locationManager: LocationManager
    private lateinit var myLocationListener: LocationListener

    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()

        if (::searchResult.isInitialized.not()) {
            intent?.let { intent ->
                searchResult = intent.getParcelableExtra(SEARCH_RESULT_EXTRA_KEY)
                    ?: throw IllegalArgumentException("데이터가 존재하지 않습니다.")

                setupGoogleMap()
                bindViews()
            }
        }
    }


    private fun bindViews() = with(binding) {
        currentLocationButton.setOnClickListener {
            getMyLocation()
        }
    }


    private fun getMyLocation() {
        if (::locationManager.isInitialized.not()) {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsEnabled) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions (
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_CODE
                )
            } else {
                setMyLocationListener()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                setMyLocationListener()
            } else {
                Toast.makeText(this, "권한을 받지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setMyLocationListener() {
        val minTime = 1500L
        val minDistance = 100f

        if (::myLocationListener.isInitialized.not()) {
            myLocationListener = MyLocationListener()
        }
        with(locationManager) {
            requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                myLocationListener
            )
            requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                myLocationListener
            )
        }
    }

    private fun onCurrentLocationChanged(locationLatLngEntity: LocationLatLngEntity) {
        // myLocationUpdate todo
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
            LatLng(
                locationLatLngEntity.latitude.toDouble(),
                locationLatLngEntity.longitude.toDouble()
            ), CAMERA_ZOOM_LEVEL))
        loadReverseGeoInformation(locationLatLngEntity)
        removeLocationListener()
    }

    private fun loadReverseGeoInformation(locationLatLngEntity: LocationLatLngEntity) {
        launch(coroutineContext) {
            try {
                withContext(Dispatchers.IO) {
                    // reverse geocoding
                    val response = RetrofitUtil.apiService.getReverseGeoCode(
                        lat = locationLatLngEntity.latitude.toDouble(),
                        lon = locationLatLngEntity.longitude.toDouble()
                    )

                    if (response.isSuccessful.not()) {
                        Toast.makeText(this@MapActivity, "reverse geocoding과정에서 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        val body = response.body()
                        withContext(Dispatchers.Main) {
                            body?.let { addressInfoResponse ->
                                val searchResultEntity = addressToSearchResultEntity(addressInfoResponse, locationLatLngEntity)

                                currentMarker = setupMarker(searchResultEntity)
                                currentMarker?.showInfoWindow()
                            }
                        }
                    }
                }
            } catch (e : Exception) {
                e.printStackTrace()
                Toast.makeText(this@MapActivity,  "error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeLocationListener() {
        if (::locationManager.isInitialized && ::myLocationListener.isInitialized) {
            locationManager.removeUpdates(myLocationListener)
        }
    }



    inner class MyLocationListener: LocationListener {
        override fun onLocationChanged(location: Location) {
            val locationLatLngEntity = LocationLatLngEntity(
                location.latitude.toFloat(),
                location.longitude.toFloat()
            )
            onCurrentLocationChanged(locationLatLngEntity)
        }
    }


    private fun setupGoogleMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        currentMarker = setupMarker(searchResult)
        currentMarker?.showInfoWindow()
    }

    private fun setupMarker(searchResultEntity: SearchResultEntity): Marker? {
        val locationLatLngEntity = searchResultEntity.locationLatLng

        val locationLatLng = LatLng (
            locationLatLngEntity.latitude.toDouble(),
            locationLatLngEntity.longitude.toDouble()
        )

        val markerOptions = MarkerOptions().apply {
            position(locationLatLng)
            title(searchResultEntity.name)
            snippet(searchResultEntity.fullAddress)
        }
        Log.e("list", searchResultEntity.fullAddress)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, CAMERA_ZOOM_LEVEL))
        return mMap.addMarker(markerOptions)
    }

    private fun addressToSearchResultEntity(addressInfoResponse: AddressInfoResponse, locationLatLngEntity: LocationLatLngEntity): SearchResultEntity {
        return SearchResultEntity(
            fullAddress = addressInfoResponse.addressInfo.fullAddress.orEmpty(),
            name = "내위치",
            locationLatLng = locationLatLngEntity
        )
    }


    companion object {
        private const val SEARCH_RESULT_EXTRA_KEY = "SEARCH_RESULT_EXTRA_KEY"
        private const val CAMERA_ZOOM_LEVEL = 17f
        private const val REQUEST_PERMISSION_CODE = 1111

        fun startMapActivity(context: Context, searchResultEntity: SearchResultEntity): Intent =
            Intent(context, MapActivity::class.java).apply {
                putExtra(SEARCH_RESULT_EXTRA_KEY, searchResultEntity)
            }
    }
}
