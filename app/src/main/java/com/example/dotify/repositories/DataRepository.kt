package com.example.dotify.repositories

import com.example.dotify.models.Account
import com.example.dotify.models.Library
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//https://raw.githubusercontent.com/echeeUW/codesnippets/master/user_info.json

class DataRepository {
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

}

interface DotifyService {
    @GET("echeeUW/codesnippets/master/user_info.json")
    suspend fun getAccount(): Account

    @GET("echeeUW/codesnippets/master/musiclibrary.json")
    suspend fun getLibrary(): Library
}