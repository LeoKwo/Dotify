package com.example.dotify.managers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.R
import com.example.dotify.activities.PlayerActivity
import com.example.dotify.activities.SettingsActivity
import com.example.dotify.models.Library
import java.util.concurrent.TimeUnit
import kotlin.random.Random

private const val NEW_UPLOADED_MUSIC_ID = "New Uploaded Music"
//private const val NEW_UPLOADED_MUSIC_KEY = "NEW_UPLOADED_MUSIC_KEY"
private const val NEW_UPLOADED_MUSIC_TITLE_KEY = "NEW_UPLOADED_MUSIC_TITLE_KEY"
private const val NEW_UPLOADED_MUSIC_ARTIST_KEY = "NEW_UPLOADED_MUSIC_ARTIST_KEY"
private const val NEW_UPLOADED_MUSIC_LARGE_IMAGE_KEY = "NEW_UPLOADED_MUSIC_LARGE_IMAGE_KEY"
private const val NEW_UPLOADED_MUSIC_SMALL_IMAGE_KEY = "NEW_UPLOADED_MUSIC_SMALL_IMAGE_KEY"
private const val NEW_UPLOADED_MUSIC_DURATION = "NEW_UPLOADED_MUSIC_DURATION"
private const val NEW_UPLOADED_MUSIC_TAG = "NEW_UPLOADED_MUSIC_TAG"

class SongNotificationManager (
    private val context: Context
){
    private var library: Library? = null

    // HANDLES WORK REQUEST

    private val workManager: WorkManager = WorkManager.getInstance(context)

    fun repetitiveSongNotification() {
        if (isRepetitiveSongNotificationRunning()) {
            workManager.cancelAllWorkByTag(NEW_UPLOADED_MUSIC_TAG)
        }

        val requestNotification = PeriodicWorkRequestBuilder<SongNotificationWorker>(20, TimeUnit.MINUTES)
                .setInitialDelay(5, TimeUnit.SECONDS)
                .setConstraints(
                        Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .build()
                )
                .addTag(NEW_UPLOADED_MUSIC_TAG)
                .build()

        workManager.enqueue(requestNotification)
    }

    fun stopRepetitiveSongNotification() {
        if (isRepetitiveSongNotificationRunning()) {
            workManager.cancelAllWorkByTag(NEW_UPLOADED_MUSIC_TAG)
            Log.i("Notification Stop", "is stopped.")
        }
    }

//    private fun stopRepetitiveNotification

    private fun isRepetitiveSongNotificationRunning(): Boolean {
        return workManager.getWorkInfosByTag(NEW_UPLOADED_MUSIC_TAG).get().any {
            when(it.state) {
                WorkInfo.State.RUNNING,
                WorkInfo.State.ENQUEUED -> true
                else -> false
            }
        }
    }

    // HANDLES NOTIFICATION BELOW

    private val notificationManager = NotificationManagerCompat.from(context)
    private var randomSong =  SongDataProvider.getAllSongs()
    init {
        initNotificationChannels()
    }

    // Add information to notification
    fun publishSongNotification() {
        val randomSong = randomSong.random()

        // Define intent when user clicks on notification
        val intent = Intent(context, PlayerActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(NEW_UPLOADED_MUSIC_TITLE_KEY, randomSong.title)
            putExtra(NEW_UPLOADED_MUSIC_ARTIST_KEY, randomSong.artist)
            putExtra(NEW_UPLOADED_MUSIC_LARGE_IMAGE_KEY, randomSong.largeImageID)
            putExtra(NEW_UPLOADED_MUSIC_SMALL_IMAGE_KEY, randomSong.smallImageID)
            putExtra(NEW_UPLOADED_MUSIC_DURATION, randomSong.durationMillis)
            putExtra(NEW_UPLOADED_MUSIC_ID, randomSong.id)
//            randomSong.
//            putExtra(NEW_UPLOADED_MUSIC_KEY, randomSong)
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Build information for Notification to show
        val builder = NotificationCompat.Builder(context, NEW_UPLOADED_MUSIC_ID) // Channel id for notification channel
                .setSmallIcon(R.drawable.ic_dotify_vec)
                .setContentTitle(context.getString(R.string.new_song_notification_title_format, randomSong.artist))
                .setContentText(context.getString(R.string.new_song_notification_content_format, randomSong.title))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent) // define what to do when user clicks
                .setAutoCancel(true) // dismiss notification automatically

        // Tell OS to publish
        with(notificationManager) {
            // notificationId is a unique int for each notification that you must define
            val notificationId = Random.nextInt()
            notify(notificationId, builder.build())
        }
    }

    private fun initNotificationChannels() {
        initSongNotification()
    }

    private fun initSongNotification() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Info about channel
            val name = context.getString(R.string.new_song_channel)
            val descriptionText = context.getString(R.string.new_song_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            // create Channel object
            val channel = NotificationChannel(NEW_UPLOADED_MUSIC_ID, name, importance).apply {
                description = descriptionText
            }
            // Create channel
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun updateLibrary(library: Library) {
        this.library  = library
    }
}