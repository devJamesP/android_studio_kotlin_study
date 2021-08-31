package com.devjamesp.hometownfinedustapp

import com.devjamesp.hometownfinedustapp.utility.AirKoreaApiService

object Url  {
    const val KAKAO_BASE_URL = "https://dapi.kakao.com"
    const val GET_COORD_URL = "/v2/local/geo/transcoord.json"

    const val AIRKOREA_BASE_URL = "http://apis.data.go.kr"
    const val GET_MONITORING_STATION_URL = "/B552584/MsrstnInfoInqireSvc/getNearbyMsrstnList"

    const val GET_STATION_AIR_QUALITY_UTL = "/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty"

}