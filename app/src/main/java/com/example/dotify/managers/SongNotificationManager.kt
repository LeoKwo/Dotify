package com.example.dotify.managers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.R
import com.example.dotify.activities.PlayerActivity
import com.example.dotify.activities.SettingsActivity
import kotlin.random.Random

private const val NEW_UPLOADED_MUSIC_ID = "New Uploaded Music"

class SongNotificationManager (
    private val context: Context
){
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
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

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
}