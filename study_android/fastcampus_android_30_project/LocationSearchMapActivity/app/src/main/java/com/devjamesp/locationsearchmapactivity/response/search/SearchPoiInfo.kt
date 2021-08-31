package com.devjamesp.locationsearchmapactivity.response.search

/*
조회 결과의 총 개수입니다.
페이지당 출력되는 개수입니다.
조회한 페이지 번호입니다.
POI 목록입니다.
 */

data class SearchPoiInfo (
    val totalCount: String,
    val count: String,
    val page: String,
    val pois: Pois
)
