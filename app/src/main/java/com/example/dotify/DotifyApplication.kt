package com.example.dotify

import android.app.Application
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class DotifyApplication: Application() {

//    val playCount: List<Int> =

    var selectedSong: Song? = null

    private var listOfSongs: List<Song> = SongDataProvider.getAllSongs()

    private val lowBound: Int = 100
    private val highBound: Int = 10000
//    private val rndPlay = (lowBound..highBound).random()
//    var playCount: Int? = rndPlay

    val playCountMap = mutableMapOf<String, Int>()


    override fun onCreate() {
        super.onCreate()
        for (song in listOfSongs) {
            playCountMap.put(song.title, (lowBound..highBound).random())
        }
    }
}