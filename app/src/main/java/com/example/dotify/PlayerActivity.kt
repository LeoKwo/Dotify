package com.example.dotify

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import java.util.*

private const val SONG_KEY = "song"
private const val PLAY_COUNT_KEY = "playCount"

fun navigateToPlayerActivity(context: Context, song: Song)  {
    val intent = Intent(context, PlayerActivity::class.java)
    val bundle = Bundle()
    bundle.putParcelable(SONG_KEY, song)
    intent.putExtras(bundle)
    context.startActivity(intent)
}

class PlayerActivity : AppCompatActivity() {
    private val lowBound: Int = 100
    private val highBound: Int = 10000
    private var playCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val prev = findViewById<ImageButton>(R.id.ibPrev)
        val next = findViewById<ImageButton>(R.id.ibNext)
        val play = findViewById<ImageButton>(R.id.ibPlay)
        val songCount = findViewById<TextView>(R.id.tvSongsPlayed)

        val btSettings = findViewById<Button>(R.id.btSettings)

        val ivAlbum = findViewById<ImageView>(R.id.ivAlbumArt)

        prev.setOnClickListener {preOnClick()}


//        NEW
        val rndPlay = (lowBound..highBound).random()
        playCount = rndPlay
        if (savedInstanceState != null) {
            songCount.text = savedInstanceState.getInt(PLAY_COUNT_KEY, 0).toString()
            playCount = savedInstanceState.getInt(PLAY_COUNT_KEY, 0)
        } else {
            songCount.text = playCount.toString()
        }
//        OG
//        val rndPlay = (lowBound..highBound).random()
//        songCount.text = rndPlay.toString()
        play.setOnClickListener {playOnClick(songCount)}

        next.setOnClickListener {nextOnClick()}

        btSettings.setOnClickListener {btSettingsOnClick()}

        val rnd = Random()
        ivAlbum.setOnLongClickListener {aaLongPress(songCount, rnd)}

        val songName = findViewById<TextView>(R.id.tvSongName)
        val artistName = findViewById<TextView>(R.id.tvArtistName)
        val launchIntent = intent

//        OG
        val song: Song? = launchIntent.extras?.getParcelable<Song>(SONG_KEY)

        if (song != null) {
            ivAlbum.setImageResource(song.largeImageID)
            songName.text = song.title
            artistName.text = song.artist
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun preOnClick() {
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_LONG).show()
    }

    private fun playOnClick(songCount: TextView) {
//        NEW
        playCount = songCount.text.toString().toInt() + 1
        songCount.text = (playCount).toString()
//        OG
//        songCount.text = (songCount.text.toString().toInt() + 1).toString()
    }

    private fun nextOnClick() {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_LONG).show()
    }

    private fun btSettingsOnClick() {
        navigateToSettingsActivity(this@PlayerActivity)
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