package com.devjamesp.imagesearchapp.data.entities

data class User(
    val bio: String? = null,
    val id: String? = null,
    val instagramUsername: String? = null,
    val links: LinksX? = null,
    val location: String? = null,
    val name: String? = null,
    val portfolioUrl: String? = null,
    val totalCollections: Int? = null,
    val totalLikes: Int? = null,
    val totalPhotos: Int? = null,
    val twitterUsername: String? = null,
    val updatedAt: String? = null,
    val username: String? = null,
    val profileImage: ProfileImageUrls? = null
)