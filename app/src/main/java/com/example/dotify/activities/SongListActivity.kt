package com.example.dotify.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import com.example.dotify.DotifyApplication
import com.example.dotify.R
import com.example.dotify.adapters.SongAdapter
import com.example.dotify.databinding.ActivitySongListBinding
import com.example.dotify.managers.MusicManager
import com.example.dotify.models.Song
import kotlinx.coroutines.launch

class SongListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongListBinding
    private lateinit var currentlyPlaying :Song
    private lateinit var adapter: SongAdapter
    private lateinit var musicManager: MusicManager
    private lateinit var listOfSongs: List<Song>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.musicManager = (this.applicationContext as DotifyApplication).musicManager

        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }


        with(binding) {
            listOfSongs = listOf()

            adapter = SongAdapter(listOfSongs)
            rvSongList.adapter = adapter

            loadLibrary()
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
                musicManager.onSongSelected(song)
            }

            btnShuffle.setOnClickListener {
                adapter.updateSongs(listOfSongs.toMutableList().shuffled())
            }
            clSongInfo.setOnClickListener {
                navigateToPlayerActivity(this@SongListActivity, currentlyPlaying)
            }

            srlRefreshSongList.setOnRefreshListener {
                loadLibrary()
                srlRefreshSongList.isRefreshing = false
            }
        }
    }

    private fun loadLibrary() {
        lifecycleScope.launch {
            runCatching {
                Toast.makeText(this@SongListActivity, "Loading your music library...", Toast.LENGTH_SHORT).show()
                val library = musicManager.dataRepository.getLibrary()
                listOfSongs = library.songs
                adapter.updateSongs(listOfSongs)
            }.onFailure {
                Toast.makeText(this@SongListActivity, "Failed..", Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(this@SongListActivity)
                        .setTitle("Error")
                        .setMessage("An error occurred when fetching your songs from the internet. Check your internet settings and app permissions.")
                        .create()
                        .show()
            }
        }
    }
}