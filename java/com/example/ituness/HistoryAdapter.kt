package com.example.ituness

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ituness.databinding.HistoryPageBinding

class HistoryAdapter(val songs : List<Song>) : RecyclerView.Adapter<HistoryAdapter.TermViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HistoryPageBinding.inflate(layoutInflater, parent, false)
        return TermViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: TermViewHolder, position: Int) {
        val song = songs[position]
        Log.d("SongName", song.toString())
        if(song.searchTerm!=null && song.searchTerm!="" && song.searchTerm!=" " && song.searchTerm!="RecentSearch")
            holder.bind(song.searchTerm!!)
        else{

        }
    }

    class TermViewHolder(private val binding : HistoryPageBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(term : String){
            binding.recentSearch.text = term
        }
    }
}