package com.devjamesp.hometownfinedustapp.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.devjamesp.hometownfinedustapp.databinding.ActivityMainBinding
import com.devjamesp.hometownfinedustapp.models.MeasuredValueModel
import com.devjamesp.hometownfinedustapp.models.MonitoringStationModel
import com.devjamesp.hometownfinedustapp.response.air_quality_entries.Grade
import com.devjamesp.hometownfinedustapp.utility.Repository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var cancellationTokenSource: CancellationTokenSource? = null

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindViews()
        initVaribles()
        requestLocationPermissions()

    }

    override fun onDestroy() {
        super.onDestroy()
        cancellationTokenSource?.cancel()
        scope.cancel()
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            requestPermissions,
            REQUEST_ACCESS_LOCATION_PERMISSIONS
        )
    }

    @SuppressLint("InlinedApi")
    private fun  requestBackgroundLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
            REQUEST_BACKGROUND_ACCESS_LOCATION_PERMISSIONS
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val locationPermissionGranted =
            requestCode == REQUEST_ACCESS_LOCATION_PERMISSIONS &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED

        val backgroundLocationPermissionGranted =
            requestCode == REQUEST_BACKGROUND_ACCESS_LOCATION_PERMISSIONS &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (backgroundLocationPermissionGranted.not()) {
                requestBackgroundLocationPermissions()
            } else {
                // fetchData
                fetchAirQualityData()
            }
        } else {
            if (locationPermissionGranted.not()) {
                finish()
            } else {
                // fetch Data
                fetchAirQualityData()
            }
        }
    }

    private fun bindViews() {
        binding.refresh.setOnRefreshListener {
            fetchAirQualityData()
        }
    }

    private fun initVaribles() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    @SuppressLint("SetTextI18n", "MissingPermission")
    private fun fetchAirQualityData() {

        cancellationTokenSource = CancellationTokenSource()

        fusedLocationProviderClient
            .getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource!!.token
            ).addOnSuccessListener { location ->
                scope.launch {
                    binding.errorDescriptionTextView.isVisible = false
                    try {
                        val monitoringStation =
                            Repository.getNearbyMonitoringStation(location.longitude, location.latitude)
                        val measuredValue =
                            Repository.getLatestAirQualityData(monitoringStation!!.stationName!!)


                        // ui
                        displayAirQualityData(monitoringStation, measuredValue)
                    } catch (e: Exception) {
                        binding.errorDescriptionTextView.isVisible = true
                        binding.contentsConstraintLayout.alpha = 0f
                    } finally {
                        binding.progressBar.isVisible = false
                        binding.refresh.isRefreshing = false

                    }
                }
            }
    }

    @SuppressLint("SetTextI18n")
    private fun displayAirQualityData(
        monitoringStation: MonitoringStationModel,
        measuredValue: MeasuredValueModel,
    ) {
        binding.contentsConstraintLayout.animate()
            .alpha(1f)
            .start()

        // 측정소 정보
        binding.measuringStationNameTextView.text = monitoringStation.stationName
        binding.measuringStationAddressTextView.text = monitoringStation.addr

        // 대기 정보
        (measuredValue.khaiGrade ?: Grade.UNKNOWN).let { grade: Grade ->
            binding.totalGradeEmojiTextView.text = grade.emoji
            binding.totalGradeLabelTextView.text = grade.label
            binding.root.setBackgroundResource(grade.colorResId)
        }

        with(measuredValue) {
            binding.fineDustInformationTextView.text =
                "미세먼지: $pm10Value ㎍/㎥${pm10Grade ?: Grade.UNKNOWN.emoji}"
            binding.ultraFineDustInformationTextView.text =
                "초미세먼지: $pm25Value ㎍/㎥${pm25Grade ?: Grade.UNKNOWN.emoji}"

            with(binding.so2Item) {
                labelTextView.text = "아황산가스"
                gradeTextView.text = (so2Grade ?: Grade.UNKNOWN.emoji).toString()
                valueTextView.text = "$so2Value ppm"
            }

            with(binding.coItem) {
                labelTextView.text = "일산화탄소"
                gradeTextView.text = (coGrade ?: Grade.UNKNOWN.emoji).toString()
                valueTextView.text = "$coValue ppm"
            }

            with(binding.o3Item) {
                labelTextView.text = "오존"
                gradeTextView.text = (o3Grade ?: Grade.UNKNOWN.emoji).toString()
                valueTextView.text = "$o3Value ppm"
            }

            with(binding.no2Item) {
                labelTextView.text = "이산화질소"
                gradeTextView.text = (no2Grade ?: Grade.UNKNOWN.emoji).toString()
                valueTextView.text = "$no2Value ppm"
            }
        }


    }

    companion object {
        private val requestPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        private const val REQUEST_ACCESS_LOCATION_PERMISSIONS = 1001
        private const val REQUEST_BACKGROUND_ACCESS_LOCATION_PERMISSIONS = 1002

    }
}