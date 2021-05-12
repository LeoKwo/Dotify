package com.example.dotify.models

data class Library (
    val title: String,
    val numOfSongs: Int,
    val songs: List<Song>
)