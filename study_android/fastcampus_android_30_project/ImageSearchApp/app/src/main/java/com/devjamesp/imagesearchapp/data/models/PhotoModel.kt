package com.devjamesp.imagesearchapp.data.models

import com.devjamesp.imagesearchapp.data.entities.*


data class PhotoModel(
    val description: String? = null,
    val height: Int = 1,
    val width: Int = 1,
    val urls: Urls? = null,
    val user: User? = null
)