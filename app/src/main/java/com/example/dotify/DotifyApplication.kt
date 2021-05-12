package com.example.dotify

import android.accounts.Account
import android.app.Application
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.managers.AccountManager
import com.example.dotify.managers.MusicManager
import com.example.dotify.repositories.DataRepository

class DotifyApplication: Application() {
    lateinit var dataRepository: DataRepository

    // Without MusicManager
//    var selectedSong: Song? = null
//
//    private var listOfSongs: List<Song> = SongDataProvider.getAllSongs()
//
//    private val lowBound: Int = 100
//    private val highBound: Int = 10000
//
//    val playCountMap = mutableMapOf<String, Int>()
//
//
//    override fun onCreate() {
//        super.onCreate()
//        for (song in listOfSongs) {
//            playCountMap.put(song.title, (lowBound..highBound).random())
//        }
//    }

    // With MusicManager
    lateinit var musicManager: MusicManager
    lateinit var accountManager: AccountManager

    override fun onCreate() {
        super.onCreate()

        this.dataRepository = DataRepository()

        this.musicManager = MusicManager()
        this.accountManager = AccountManager()

    }

}