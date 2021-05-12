package com.example.dotify.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.dotify.DotifyApplication
import com.example.dotify.R
import com.example.dotify.databinding.FragmentStatsBinding
import com.example.dotify.managers.MusicManager
import com.example.dotify.models.Library
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

class StatsFragment : Fragment() {

//    private val navController by lazy { findNavController() }
//    private val safeArgs: StatsFragmentArgs by navArgs()
    lateinit var musicManager: MusicManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.musicManager = MusicManager()


        val binding = FragmentStatsBinding.inflate(inflater)

        musicManager = (activity?.applicationContext as DotifyApplication).musicManager
//        with(binding) {

//            ivStatsAlbumArt.setImageResource(safeArgs.song.largeImageID)
//            tvStatsPlayCount.text = root.context.getString(
//                    R.string.stats_song_stats_format,
//                safeArgs.song.title,
//                safeArgs.song.artist,
//                safeArgs.playCount.toString())

//            ivStatsAlbumArt = library.dataRepository.getLibrary()
        loadStats(binding)
//        }
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