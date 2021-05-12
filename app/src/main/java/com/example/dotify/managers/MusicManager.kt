package com.example.dotify.managers

import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class MusicManager {

    private val lowBound: Int = 100
    private val highBound: Int = 10000

    var selectedSong: Song? = null
        private set

    var listOfSongs: List<Song> = SongDataProvider.getAllSongs()
    val playCountMap = mutableMapOf<String, Int>()

    fun onSongSelected(song: Song) {
        selectedSong = song
    }

    // Initialize playCountMap
    init {
        for (song in listOfSongs) {
            playCountMap[song.title] = (lowBound..highBound).random()
        }
    }
}