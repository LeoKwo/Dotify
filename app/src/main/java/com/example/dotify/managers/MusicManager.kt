package com.example.dotify.managers

import com.example.dotify.models.Song
import com.example.dotify.repositories.DataRepository

class MusicManager {

    var dataRepository = DataRepository()

    var selectedSong: Song? = null
        private set

    val playCountMap = mutableMapOf<String, Int>()

    fun onSongSelected(song: Song) {
        selectedSong = song
    }
}