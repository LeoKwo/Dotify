package com.example.dotify.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dotify.R
import com.example.dotify.SongDiffCallback
import com.example.dotify.databinding.SongListItemBinding
import com.example.dotify.models.Song

class SongAdapter(private var listOfSongs: List<Song>): RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    var onSongClickListener: (song: Song) -> Unit = {_ ->}

    class SongViewHolder(val binding: SongListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = SongListItemBinding.inflate(LayoutInflater.from(parent.context))
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = listOfSongs[position]
        with(holder.binding) {
            tvListSongName.text = song.title
            tvListArtistName.text = song.artist

            ivAlbumArtSmall.load(song.smallImageURL) {
                crossfade(true)
                placeholder(R.drawable.iv_account_placeholder)
            }

            clSongListItem.setOnClickListener{
                onSongClickListener(song)
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfSongs.size
    }

    fun updateSongs(newListOfSongs: List<Song>) {
        val callback = SongDiffCallback(newListOfSongs, listOfSongs)
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)

        listOfSongs = newListOfSongs
    }
}