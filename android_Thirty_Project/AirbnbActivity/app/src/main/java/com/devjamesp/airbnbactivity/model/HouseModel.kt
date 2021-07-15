package com.devjamesp.airbnbactivity.model

import kotlinx.parcelize.Parcelize


data class HouseModel(
    val id : Int,
    val title : String,
    val price : String,
    val lat : Double,
    val lng : Double,
    val imgUrl : String
)
