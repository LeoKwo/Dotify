package com.example.dotify.managers

import com.example.dotify.DotifyApplication
import com.example.dotify.models.Account
import com.example.dotify.repositories.DataRepository
import com.google.gson.Gson

class AccountManager() {

//    var username: String
//    var firstName: String? = null
//        private set
//    var lastName: String? = null
//        private set
//    var hasNose: Boolean? = null
//        private set
//    var platform: Double = 1.0
//    var profilePicURL: String? = null
//        private set


//    private val gson: Gson = Gson()
//    private val account = gson.fromJson(accountJsonString, Account::class.java)
    var dataRepository = DataRepository()

//    private val account = dataRepository.getAccount()

//    init {
//        username = account.username
//        firstName = account.firstName
//        lastName = account.lastName
//        hasNose = account.hasNose
//        platform = account.platform
//        profilePicURL = account.profilePicURL
//    }

    // old
//    private val accountJson = JSONObject(accountJsonString)
//    var username: String? = null
//        private set
//
//    init {
//
//        val accountInfoArray = arrayOf(
//                "username",
//                "firstname",
//                "lastname",
//                "hasNose",
//                "platform",
//                "profilePicURL"
//        )
//
//        kotlin.runCatching {
//            if (accountJson.has(accountInfoArray[0 ])) {
//                username = accountJson.getString(accountInfoArray[0])
//            }
//        }.onFailure {
//            username = "Error: Invalid JSON"
//        }
//    }

}
