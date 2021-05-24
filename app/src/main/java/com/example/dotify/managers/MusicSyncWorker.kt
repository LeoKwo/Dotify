package com.example.dotify.managers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.dotify.DotifyApplication
import java.lang.Exception

class MusicSyncWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    private val application by lazy { context.applicationContext as DotifyApplication }
    private val dataRepository by lazy { application.dataRepository }
    private val songNotificationManager by lazy { application.songNotificationManager }

    override suspend fun doWork(): Result {
        return try {
            val library = dataRepository.getLibrary()
            songNotificationManager.updateLibrary(library)
            Result.success()
        }catch(ex: Exception){
            Result.failure()
        }
    }
}