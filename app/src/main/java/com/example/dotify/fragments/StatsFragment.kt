package com.example.dotify.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.dotify.DotifyApplication
import com.example.dotify.R
import com.example.dotify.databinding.FragmentStatsBinding
import com.example.dotify.managers.MusicManager
import kotlinx.coroutines.launch

class StatsFragment : Fragment() {

    lateinit var musicManager: MusicManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.musicManager = MusicManager()


        val binding = FragmentStatsBinding.inflate(inflater)

        musicManager = (activity?.applicationContext as DotifyApplication).musicManager

        loadStats(binding)

        return binding.root
    }

    private fun loadStats(binding: FragmentStatsBinding) {
        with(binding) {
            lifecycleScope.launch {
                runCatching {
                    Toast.makeText(
                        activity,
                        "Loading Statistics of your song...",
                        Toast.LENGTH_SHORT
                    ).show()
                    val selectedSong = musicManager.selectedSong
                    val playCounter = musicManager.playCountMap
                    if (selectedSong != null) {
                        ivStatsAlbumArt.load(selectedSong.largeImageURL) {
                            crossfade(true)
                            placeholder(R.drawable.iv_account_placeholder)
                        }
                        tvStatsPlayCount.text = root.context.getString(
                            R.string.stats_song_stats_format,
                            selectedSong.title,
                            selectedSong.artist,
                            playCounter[selectedSong.title].toString())
                    } else {
                        Log.i("selectedSong", "is null")
                    }
                }.onFailure {
                    Toast.makeText(
                        activity,
                        "Failed..",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}