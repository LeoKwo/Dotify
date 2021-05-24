package com.example.dotify.managers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.dotify.DotifyApplication
import java.lang.Exception

class SongNotificationWorker(
        private val context: Context,
        workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    private val application by lazy { context.applicationContext as DotifyApplication}
    private val dataRepository by lazy { application.dataRepository }
    private val songNotificationManager by lazy { application.songNotificationManager }

    override suspend fun doWork(): Result {
//        Log.i("NotificationWorker", "notify now")

        return try {
            songNotificationManager.publishSongNotification()

            val library = dataRepository.getLibrary()
//            dataRepository.addNewSong
            songNotificationManager.updateLibrary(library)
            Result.success()
        }catch(ex: Exception){
            Result.failure()
        }
    }
}