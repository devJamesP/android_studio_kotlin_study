package com.devjamesp.imagesearchapp.data.entities

data class PhotoResponse(
    val blurHash: String? = null,
    val color: String? = null,
    val createdAt: String? = null,
    val currentUserCollections: List<CurrentUserCollection>? = null,
    val description: String? = null,
    val downloads: Int? = null,
    val exif: Exif? = null,
    val height: Int = 0,
    val id: String? = null,
    val likedByUser: Boolean? = null,
    val likes: Int? = null,
    val links: Links? = null,
    val location: Location? = null,
    val updatedAt: String? = null,
    val urls: Urls? = null,
    val user: User? = null,
    val width: Int = 0
)