package com.example.dotify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.dotify.R
import com.example.dotify.databinding.FragmentStatsBinding

class StatsFragment : Fragment() {

//    private val navController by lazy { findNavController() }
    private val safeArgs: StatsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStatsBinding.inflate(inflater)
        with(binding) {
            ivStatsAlbumArt.setImageResource(safeArgs.song.largeImageID)
            tvStatsPlayCount.text = root.context.getString(
                    R.string.stats_song_stats_format,
                safeArgs.song.title,
                safeArgs.song.artist,
                safeArgs.playCount.toString())
        }
        return binding.root
    }
}