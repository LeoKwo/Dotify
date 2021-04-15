package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.core.view.isInvisible

import com.ericchee.songdataprovider.SongDataProvider
import com.ericchee.songdataprovider.Song
import com.example.dotify.databinding.ActivitySongListBinding


class SongListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongListBinding
    private lateinit var currentlyPlaying :Song

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }

        val listOfSongs = SongDataProvider.getAllSongs()

        with(binding) {
//            SongAdapter(listOfSongs)
            val adapter = SongAdapter(listOfSongs)
            rvSongList.adapter = adapter
            adapter.onSongClickListener = { song ->
//                Toast.makeText(this@SongListActivity, "Pos: $pos", Toast.LENGTH_SHORT).show()
//                val title = song.title
//                val artist = song.artist
                tvSongInfo.text = root.context.getString(R.string.song_info_format, song.title, song.artist)
//                tvInfoArtistName.text = song.artist
                clSongInfo.isInvisible = false
                currentlyPlaying = song

//                navigateToMainActivity(this@SongListActivity, song)
            }

            btnShuffle.setOnClickListener {
                adapter.updateSongs(listOfSongs.toMutableList().shuffled())
//                adapter.notifyDataSetChanged()
            }

//            navigateToMainActivity(this@SongListActivity, song)
            clSongInfo.setOnClickListener {
                navigateToMainActivity(this@SongListActivity, currentlyPlaying)
            }
            // hide SongInfo Panel by default
            clSongInfo.isInvisible = true
        }




    }

}