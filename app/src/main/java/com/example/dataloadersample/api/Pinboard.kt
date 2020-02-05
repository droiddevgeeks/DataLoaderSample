package com.example.dataloadersample.api


data class PinBoard(
    val id: String,
    val created_at: String,
    val width: Int,
    val height: Int,
    val color: String,
    val likes: Int,
    val liked_by_user: Boolean,
    val user: User,
    val urls: Urls,
    val categories: List<Category>,
    val links: Links
)

data class Category(
    val id: Int,
    val title: String,
    val photo_count: Int,
    val links: Links
)

data class Links(
    val self: String?,
    val photos: String?,
    val html: String?,
    val download: String?
)

data class ProfileImage(
    val small: String,
    val medium: String,
    val large: String
)

data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class User(
    val id: String,
    val username: String,
    val name: String,
    val profile_image: ProfileImage,
    val links: Links
)