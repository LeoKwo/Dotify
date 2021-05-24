package com.example.dotify

import android.app.Application
import com.example.dotify.managers.AccountManager
import com.example.dotify.managers.MusicManager
import com.example.dotify.managers.SongNotificationManager
import com.example.dotify.repositories.DataRepository

class DotifyApplication: Application() {
    lateinit var dataRepository: DataRepository
    lateinit var musicManager: MusicManager
    lateinit var accountManager: AccountManager
    lateinit var songNotificationManager: SongNotificationManager

    override fun onCreate() {
        super.onCreate()

        this.dataRepository = DataRepository()

        this.musicManager = MusicManager()
        this.accountManager = AccountManager()

        this.songNotificationManager = SongNotificationManager(this)
    }
}