package com.example.dotify.models

import android.os.Parcel
import android.os.Parcelable


data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val durationMillis: Int,
    val smallImageURL: String? = null,
    val largeImageURL: String? = null
)
