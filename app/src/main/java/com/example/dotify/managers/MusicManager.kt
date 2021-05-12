package com.example.dotify.managers

//import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.models.Song
import com.example.dotify.repositories.DataRepository

class MusicManager {

    private val lowBound: Int = 100
    private val highBound: Int = 10000

    var dataRepository = DataRepository()

    var selectedSong: Song? = null
        private set

//    var listOfSongs: List<Song> = SongDataProvider.getAllSongs()
//    lateinit var listOfSongs: List<Song>
    val playCountMap = mutableMapOf<String, Int>()

    // Initialize playCountMap
//    init {
//        listOfSongs
//
//        for (song in listOfSongs) {
//            playCountMap[song.title] = (lowBound..highBound).random()
//        }
//    }

    fun onSongSelected(song: Song) {
        selectedSong = song
    }
}