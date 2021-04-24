package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isInvisible
import com.ericchee.songdataprovider.SongDataProvider
import com.ericchee.songdataprovider.Song
import com.example.dotify.databinding.ActivitySongListBinding

private const val CURRENTLY_PLAYING_KEY = "currentlyPlaying"

class SongListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongListBinding
    private lateinit var currentlyPlaying :Song
//    private val currentlyPlayingKey: String = "currentlyPlaying"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }

        val listOfSongs = SongDataProvider.getAllSongs()

        with(binding) {

            val adapter = SongAdapter(listOfSongs)
            rvSongList.adapter = adapter

            if (savedInstanceState != null) {
                val savedCurrentlyPlaying = savedInstanceState.getParcelable<Song>(CURRENTLY_PLAYING_KEY)
                if (savedCurrentlyPlaying != null ) {
                    clSongInfo.isInvisible = false
                    currentlyPlaying = savedCurrentlyPlaying
                    tvSongInfo.text = root.context.getString(R.string.song_info_format, savedCurrentlyPlaying.title, savedCurrentlyPlaying.artist)
                }
            } else {
                clSongInfo.isInvisible = true
            }

            adapter.onSongClickListener = { song ->
                tvSongInfo.text = root.context.getString(R.string.song_info_format, song.title, song.artist)
                clSongInfo.isInvisible = false
                currentlyPlaying = song
            }

            btnShuffle.setOnClickListener {
                adapter.updateSongs(listOfSongs.toMutableList().shuffled())
            }
            clSongInfo.setOnClickListener {
                navigateToPlayerActivity(this@SongListActivity, currentlyPlaying)
            }

        }

// OG
//            adapter.onSongClickListener = { song ->
//                tvSongInfo.text = root.context.getString(R.string.song_info_format, song.title, song.artist)
//                clSongInfo.isInvisible = false
//                currentlyPlaying = song
//            }
//            btnShuffle.setOnClickListener {
//                adapter.updateSongs(listOfSongs.toMutableList().shuffled())
//            }
//            clSongInfo.setOnClickListener {
//                navigateToMainActivity(this@SongListActivity, currentlyPlaying)
//            }
//            clSongInfo.isInvisible = true
//        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(CURRENTLY_PLAYING_KEY, currentlyPlaying)
        super.onSaveInstanceState(outState)
    }

}