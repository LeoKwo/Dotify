package com.example.dotify.models

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val durationMillis: Int,
    val smallImageURL: String? = null,
    val largeImageURL: String? = null
)
