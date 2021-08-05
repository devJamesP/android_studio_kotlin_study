package com.devjamesp.imagesearchapp.data.models

import com.devjamesp.imagesearchapp.data.entities.*

fun List<PhotoResponse>?.mapToPhototDataList(): List<PhotoModel>? {
    if (this == null) return null

    val photoDataList = mutableListOf<PhotoModel>()
    for (photoResponse in this) {
        photoDataList.add(photoResponse.mapToPhotoData())
    }
    return photoDataList
}

private fun PhotoResponse.mapToPhotoData(): PhotoModel =
    PhotoModel(description, height, width, urls, user)