package com.example.dotify.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
//import com.ericchee.songdataprovider.SongDataProvider
//import com.ericchee.songdataprovider.Song
import com.example.dotify.DotifyApplication
import com.example.dotify.R
import com.example.dotify.adapters.SongAdapter
import com.example.dotify.databinding.ActivitySongListBinding
import com.example.dotify.managers.MusicManager
import com.example.dotify.models.Song
import kotlinx.coroutines.launch

private const val CURRENTLY_PLAYING_KEY = "currentlyPlaying"

class SongListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongListBinding
    private lateinit var currentlyPlaying :Song
    private lateinit var adapter: SongAdapter
//    private lateinit var dotifyApp: DotifyApplication
    private lateinit var musicManager: MusicManager
    private lateinit var listOfSongs: List<Song>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        dotifyApp = this.applicationContext as DotifyApplication
        this.musicManager = (this.applicationContext as DotifyApplication).musicManager

        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }

        // old
//        val listOfSongs = SongDataProvider.getAllSongs()

        // new
//        val listOfSongs = musicManager.dataRepository.getLibrary()
//        val library = musicManager.dataRepository.getLibrary()

        with(binding) {
            listOfSongs = listOf()

            adapter = SongAdapter(listOfSongs)
            rvSongList.adapter = adapter

            loadLibrary()
//            if (savedInstanceState != null) {
////                val savedCurrentlyPlaying = savedInstanceState.getParcelable<Song>(CURRENTLY_PLAYING_KEY)
//                val savedCurrentlyPlaying = listOfSongs.find {it.id == savedInstanceState.getInt(CURRENTLY_PLAYING_KEY)}
//                if (savedCurrentlyPlaying != null ) {
//                    clSongInfo.isInvisible = false
//                    currentlyPlaying = savedCurrentlyPlaying
//                    tvSongInfo.text = root.context.getString(R.string.song_info_format, savedCurrentlyPlaying.title, savedCurrentlyPlaying.artist)
//                }
//            } else {
//                clSongInfo.isInvisible = true
//            }
            val selectedSong = musicManager.selectedSong
            if (selectedSong != null) {
                clSongInfo.isInvisible = false
                currentlyPlaying = selectedSong
                tvSongInfo.text = root.context.getString(R.string.song_info_format, selectedSong.title, selectedSong.artist)

            } else {
                clSongInfo.isInvisible = true
            }

            adapter.onSongClickListener = { song ->
                tvSongInfo.text = root.context.getString(R.string.song_info_format, song.title, song.artist)
                clSongInfo.isInvisible = false
                currentlyPlaying = song

//                musicManager.selectedSong = song
                musicManager.onSongSelected(song)
            }

            btnShuffle.setOnClickListener {
                adapter.updateSongs(listOfSongs.toMutableList().shuffled())
            }
            clSongInfo.setOnClickListener {
                navigateToPlayerActivity(this@SongListActivity, currentlyPlaying)
            }
        }
    }

//    override fun onSaveInstanceState(outState: Bundle) {
////        outState.putParcelable(CURRENTLY_PLAYING_KEY, currentlyPlaying)
//        outState.putInt(CURRENTLY_PLAYING_KEY, currentlyPlaying.id)
//        super.onSaveInstanceState(outState)
//    }

    private fun loadLibrary() {
        lifecycleScope.launch {
            runCatching {
                Toast.makeText(this@SongListActivity, "Loading your music library...", Toast.LENGTH_SHORT).show()
                val library = musicManager.dataRepository.getLibrary()
                listOfSongs = library.songs
                adapter.updateSongs(listOfSongs)
            }.onFailure {
                Toast.makeText(this@SongListActivity, "Failed..", Toast.LENGTH_SHORT).show()
                // TODO: Do something here
            }
        }
    }

}