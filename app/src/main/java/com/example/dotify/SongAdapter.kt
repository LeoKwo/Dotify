package com.example.dotify

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import com.example.dotify.databinding.SongListItemBinding

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

//            may need to change this
            ivAlbumArtSmall.setImageResource(song.smallImageID)

            clSongListItem.setOnClickListener{
                onSongClickListener(song)
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfSongs.size
    }

    fun updateSongs(newListOfSongs: List<Song>) {
//        listOfSongs = newListOfSongs
//        notifyDataSetChanged()
        val callback = SongDiffCallback(newListOfSongs, listOfSongs)
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)

        listOfSongs = newListOfSongs
    }

//    class SongViewHolder(val binding: SongListItemBinding): RecyclerView.ViewHolder(binding.root)
}