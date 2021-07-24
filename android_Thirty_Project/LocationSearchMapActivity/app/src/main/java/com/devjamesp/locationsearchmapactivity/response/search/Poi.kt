package com.devjamesp.locationsearchmapactivity.response.search

data class Poi (
    // POI ID입니다.
    val id: String? = null,

    // name
    val name: String? = null,

    // 전화번호
    val telNo: String? = null,

    // 시설물 입구 위도 좌표
    val frontLat: Float = 0.0f,

    // 시설물 입구 경도 좌표입니다.
    val frontLon: Float = 0.0f,

    // 중심점 위도 좌표입니다.
    val noorLat: Float = 0.0f,

    // 중심점 경도 좌표입니다.
    val noorLon: Float = 0.0f,

    // 표출 주소 대분류명입니다.
    val upperAddrName: String? = null,

    // 표출 주소 중분류명입니다.
    val middleAddrName: String? = null,

    // 표출 주소 소분류명입니다.
    val lowerAddrName: String? = null,

    // 표출 주소 세분류명입니다.
    val detailAddrName: String? = null,

    // 본번입니다.
    val firstNo: String? = null,

    // 부번입니다
    val secondNo: String? = null,

    // 도로명에 해당하는 명칭입니다.
    val roadName: String? = null,

    // 건물번호1(새주소)
    val firstBuildNo: String? = null,

    // 건물번호2(새주소)
    val secondBuildNo: String? = null,

    // 업종 대분류명
    val mlClass: String? = null,

    // 거리 (요청 좌표에서 떨어진 거리) 단위 : km
    val radius: String? = null,

    // 대표 업종명입니다.
    val bizName: String? = null,

    // 업종 대분류명입니다.
    val upperBizName: String? = null,

    // 업종 중분류명입니다.
    val middleBizeName: String? = null,

    // 업종 소분류명입니다.
    val lowerBizName: String? = null,

    // 업종 상세분류명입니다.
    val detailBizName: String? = null,

    // RP_FLAG는 길안내 요청에 필요한 값입니다. - 길안내 목적지 설정 시, 여기서 받은 rpFlag 값을 설정해야 합니다.
    val rpFlag: String? = null,

    // 	주차 가능유무입니다. - 0: 주차 불가능 - 1: 주차 가능 - null: 정보없음
    val parkFlag: String? = null,

    // POI 상세정보 유무입니다. 해당 필드가 1로 설정되어 있으면, POI 상세정보 API를 이용하여
    // 상세 정보를 조회할 수 있습니다. - 0: 없음 - 1: 있음 - null: 정보없음
    val detailInfoFlag: String? = null,

    // 소개정보입니다.
    val desc: String? = null
)
