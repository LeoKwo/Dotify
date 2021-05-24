package com.example.dotify.repositories

import com.example.dotify.models.Account
import com.example.dotify.models.Library
import com.example.dotify.models.Song
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class DataRepository {
    private var newSongs = mutableListOf<Song>()

    private val DotifyService = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DotifyService::class.java)

    suspend fun getAccount(): Account {
        return DotifyService.getAccount()
    }

    suspend fun getLibrary(): Library {
        return DotifyService.getLibrary()
    }

    suspend fun addNewSongToLibrary(newSong: Song): Library {
        newSongs.add(newSong)
        val oldLibrary = DotifyService.getLibrary()
        oldLibrary.songs.toMutableList().addAll(newSongs)
        val newLibrary = Library(
            oldLibrary.title,
            oldLibrary.numOfSongs + 1,
            oldLibrary.songs
        )
        return newLibrary
//        oldLibrary.songs.toMutableList().addAll(newSongs)
//        oldLibrary.numOfSongs + 1
    }
}

interface DotifyService {
    @GET("echeeUW/codesnippets/master/user_info.json")
    suspend fun getAccount(): Account

    @GET("echeeUW/codesnippets/master/musiclibrary.json")
    suspend fun getLibrary(): Library
}