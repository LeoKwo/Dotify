package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dotify.databinding.ActivitySongListBinding

class SongListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }

        with(binding) {
            rvSongList.adapter =
        }

    }

}