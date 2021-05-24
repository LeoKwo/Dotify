package com.example.dotify.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.dotify.DotifyApplication
import com.example.dotify.R
import com.example.dotify.managers.MusicManager
import com.example.dotify.models.Song
import java.util.*

private const val SONG_KEY = "song"
private const val PLAY_COUNT_KEY = "playCount"
private const val NEW_UPLOADED_MUSIC_TITLE_KEY = "NEW_UPLOADED_MUSIC_TITLE_KEY"
private const val NEW_UPLOADED_MUSIC_ARTIST_KEY = "NEW_UPLOADED_MUSIC_ARTIST_KEY"
private const val NEW_UPLOADED_MUSIC_IMAGE_KEY = "NEW_UPLOADED_MUSIC_IMAGE_KEY"

fun navigateToPlayerActivity(context: Context, song: Song)  {
    val intent = Intent(context, PlayerActivity::class.java)
    val bundle = Bundle()
    bundle.putString(SONG_KEY, song.id)
    intent.putExtras(bundle)
    context.startActivity(intent)
}

class PlayerActivity : AppCompatActivity() {
    private val lowBound: Int = 100
    private val highBound: Int = 1999
    private var playCount: Int = 0

    private lateinit var song: Song
    private lateinit var musicManager: MusicManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)


        val prev = findViewById<ImageButton>(R.id.ibPrev)
        val next = findViewById<ImageButton>(R.id.ibNext)
        val play = findViewById<ImageButton>(R.id.ibPlay)
        val songCount = findViewById<TextView>(R.id.tvSongsPlayed)

        val btSettings = findViewById<Button>(R.id.btSettings)

        val ivAlbum = findViewById<ImageView>(R.id.ivAlbumArt)

        prev.setOnClickListener { preOnClick() }

        play.setOnClickListener { playOnClick(songCount) }

        next.setOnClickListener { nextOnClick() }

        btSettings.setOnClickListener { btSettingsOnClick() }

        val rnd = Random()
        ivAlbum.setOnLongClickListener { aaLongPress(songCount, rnd) }


        val songName = findViewById<TextView>(R.id.tvSongName)
        val artistName = findViewById<TextView>(R.id.tvArtistName)
        this.musicManager = (this.applicationContext as DotifyApplication).musicManager


        val counter = musicManager.playCountMap
        val selectedSong = musicManager.selectedSong

        val notifiedTitle = intent.getStringExtra(NEW_UPLOADED_MUSIC_TITLE_KEY)
        val notifiedArtist = intent.getStringExtra(NEW_UPLOADED_MUSIC_ARTIST_KEY)
        val notifiedImage = intent.getIntExtra(NEW_UPLOADED_MUSIC_IMAGE_KEY, 0)

        // Add a back to song list button
        val btBackToSongList = findViewById<Button>(R.id.btBackToSongList)
        val intentSongList = Intent(this, SongListActivity::class.java)
        btBackToSongList.setOnClickListener{
            this.startActivity(intentSongList)
        }

        // When the user clicks on a notification
        if (notifiedTitle != null && notifiedArtist != null) {
            songName.text = notifiedTitle
            artistName.text = notifiedArtist
            ivAlbum.load(notifiedImage) {
                crossfade(true)
                placeholder(R.drawable.iv_account_placeholder)
            }
            // Settings button is not working, so I disable it to prevent accidental clicking
            btSettings.visibility = View.INVISIBLE

            // Disables back button because it exits the app instead of going back to song list
            supportActionBar?.setDisplayHomeAsUpEnabled(false)

            // Reveals the back to collection button
            btBackToSongList.visibility = View.VISIBLE
        } else {

            // When there is a song playing
            if (selectedSong != null) {
                val count = counter[selectedSong?.title]
                if (count != null) {
                    playCount = count
                } else {
                    playCount = (lowBound..highBound).random()
                    counter[selectedSong.title] = playCount
                }

                songName.text = selectedSong.title
                artistName.text = selectedSong.artist
                this.song = selectedSong

                ivAlbum.load(selectedSong.largeImageURL) {
                    crossfade(true)
                    placeholder(R.drawable.iv_account_placeholder)
                }

            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        // Rotate, recreate, etc.
        if (savedInstanceState != null) {
            songCount.text = savedInstanceState.getInt(PLAY_COUNT_KEY, 0).toString()
            playCount = savedInstanceState.getInt(PLAY_COUNT_KEY, 0)
        } else {
            songCount.text = playCount.toString()
        }


//        if (selectedSong != null) {
//            songName.text = selectedSong.title
//            artistName.text = selectedSong.artist
//            this.song = selectedSong
//        }
    }

    private fun preOnClick() {
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_LONG).show()
    }

    private fun playOnClick(songCount: TextView) {
        playCount = songCount.text.toString().toInt() + 1
        songCount.text = (playCount).toString()
        val selectedSong = musicManager.selectedSong
        if (selectedSong != null) {
            musicManager.playCountMap[selectedSong.title] = playCount
        }
    }

    private fun nextOnClick() {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_LONG).show()
    }

    private fun btSettingsOnClick() {
        startSettingsActivity(this@PlayerActivity, song, playCount)
    }

    private fun aaLongPress(song: TextView, rnd: Random):Boolean {
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        song.setTextColor(color)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(PLAY_COUNT_KEY, playCount)
        super.onSaveInstanceState(outState)
    }
}