package com.devjamesp.locationsearchmapactivity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.devjamesp.locationsearchmapactivity.databinding.ActivityMainBinding
import com.devjamesp.locationsearchmapactivity.model.LocationLatLngEntity
import com.devjamesp.locationsearchmapactivity.model.SearchResultEntity
import com.devjamesp.locationsearchmapactivity.response.search.Poi
import com.devjamesp.locationsearchmapactivity.response.search.SearchResponse
import com.devjamesp.locationsearchmapactivity.utility.RetrofitUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    lateinit var job : Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()

        initAdapter()
        initViews()
        bindViews()
        initData()
    }
    private fun initViews() = with(binding) {
        emptyResultTextView.isVisible = false
        searchRecyclerView.adapter = adapter

        keywordSearchEditText
        searchButton
    }

    private fun bindViews() = with(binding) {
        searchButton.setOnClickListener {
            searchKeyword(keywordSearchEditText.text.toString())
        }
    }

    private fun initAdapter() {
        adapter = SearchRecyclerViewAdapter()
    }

    private fun initData() {
        adapter.notifyDataSetChanged()
    }

    private fun searchKeyword(keywordString: String) {
        launch(coroutineContext) {
            try {
                withContext(Dispatchers.IO) {
                    val response = RetrofitUtil.apiService.getSearchLocation(
                        keyword = keywordString
                    )
                    if (response.isSuccessful.not()) {
                        Toast.makeText(this@MainActivity, "검색하는 과정에서 에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        val body = response.body()
                        withContext(Dispatchers.Main) {
                            body?.let { searchResponse ->
                                setData(resonseToPoi(searchResponse))
                            }
                        }
                    }
                }
            } catch (e : Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "검색하는 과정에서 에러가 발생했습니다. : ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setData(listPoi: List<Poi>) {
        val dataList = listPoi.map { poi -> poiToSearchResultEntity(poi) }

        adapter.setSearchResultList(dataList) {
            Toast.makeText(this, "빌딩이름: ${it.name}, 주소: ${it.fullAddress}, 위도/경도: (${it.locationLatLng.latitude} : ${it.locationLatLng.longitude})", Toast.LENGTH_SHORT).show()
            MapActivity.startMapActivity(this@MainActivity, it).also { intent ->
                startActivity(intent)
            }
        }
    }

    private fun resonseToPoi(response: SearchResponse) : List<Poi> {
        return response.searchPoiInfo.pois.poi
    }

    private fun poiToSearchResultEntity(poi: Poi): SearchResultEntity =
        SearchResultEntity(
            fullAddress = makeMainAddress(poi),
            name = poi.name.orEmpty(),
            locationLatLng = poiToLocationLatLngEntity(poi)
        )

    private fun poiToLocationLatLngEntity(poi: Poi): LocationLatLngEntity =
        LocationLatLngEntity (
            latitude = poi.noorLat,
            longitude = poi.noorLon
        )

    private fun makeMainAddress(poi: Poi): String =
        if (poi.secondBuildNo?.trim().isNullOrEmpty()) {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    poi.firstNo?.trim()
        } else {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    (poi.firstNo?.trim() ?: "") + " " +
                    poi.secondNo?.trim()
        }
}