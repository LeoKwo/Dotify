package com.example.dotify

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import java.util.*

private const val SONG_KEY = "song"

fun navigateToMainActivity(context: Context, song: Song)  {
    val intent = Intent(context, MainActivity::class.java)
    val bundle = Bundle()
    bundle.putParcelable(SONG_KEY, song)
    intent.putExtras(bundle)
    context.startActivity(intent)
}

class MainActivity : AppCompatActivity() {
    private val lowBound: Int = 100
    private val highBound: Int = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val prev = findViewById<ImageButton>(R.id.ibPrev)
        val next = findViewById<ImageButton>(R.id.ibNext)
        val play = findViewById<ImageButton>(R.id.ibPlay)
        val songCount = findViewById<TextView>(R.id.tvSongsPlayed)

//        val username = findViewById<TextView>(R.id.tvUserName)
//        val newUsername = findViewById<EditText>(R.id.etUserName)
        val btSettings = findViewById<Button>(R.id.btSettings)

        val ivAlbum = findViewById<ImageView>(R.id.ivAlbumArt)

//        newUsername.visibility = View.INVISIBLE

        prev.setOnClickListener {preOnClick()}

        val rndPlay = (lowBound..highBound).random()
        songCount.text = rndPlay.toString()
        play.setOnClickListener {playOnClick(songCount)}

        next.setOnClickListener {nextOnClick()}

        btSettings.setOnClickListener {btSettingsOnClick()}

        val rnd = Random()
        ivAlbum.setOnLongClickListener {aaLongPress(songCount, rnd)}

        val songName = findViewById<TextView>(R.id.tvSongName)
        val artistName = findViewById<TextView>(R.id.tvArtistName)
        val launchIntent = intent
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
        songCount.text = (songCount.text.toString().toInt() + 1).toString()
    }

    private fun nextOnClick() {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_LONG).show()
    }

    private fun btSettingsOnClick() {

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

}