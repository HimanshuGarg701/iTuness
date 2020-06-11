package com.example.ituness

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ituness.databinding.HomePageBinding
import com.squareup.picasso.Picasso

class SongListAdapter(private var songs: List<Song>) : RecyclerView.Adapter<SongListAdapter.SongsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HomePageBinding.inflate(layoutInflater, parent, false)
        return SongsViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song)
        holder.song = song
    }

    class SongsViewHolder(private val binding : HomePageBinding, var song : Song?=null) : RecyclerView.ViewHolder(binding.root){

        init{
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, SongDetails::class.java)

                intent.putExtra("songName", song!!.trackName)
                intent.putExtra("previewUrl", song!!.previewUrl)
                intent.putExtra("singer", song!!.artistName)
                intent.putExtra("image", song!!.artworkUrl100)
                intent.putExtra("album", song!!.collectionName)
                Log.d("SongDetails", song.toString())
                binding.root.context.startActivity(intent)
            }
        }

        fun bind(song: Song){
            if(song.collectionName !=null){
                binding.albumTitle.text = "Album: ${song.collectionName}"
            }
            else{
                binding.albumTitle.text = "Album: N/A"
            }

            if(song.artistName !=null){
                binding.singerTitle.text = "By: ${song.artistName}"
            }
            else{
                binding.singerTitle.text = "By: N/A"
            }


            if(song.trackName !=null){
                binding.songTitle.text = "Song: ${song.trackName}"
            }
            else{
                binding.songTitle.text = "Song: N/A"
            }

            if(song.artworkUrl100 !=null){
                Picasso.get().load(song.artworkUrl100).into(binding.albumImage)
            }
        }
    }
}