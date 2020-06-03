package com.example.ituness

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ituness.databinding.HistoryPageBinding

class HistoryAdapter(val recents : List<String>) : RecyclerView.Adapter<HistoryAdapter.TermViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HistoryPageBinding.inflate(layoutInflater, parent, false)
        return TermViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recents.size
    }

    override fun onBindViewHolder(holder: TermViewHolder, position: Int) {
        val recent = recents[position]
        holder.bind(recent)

    }

    class TermViewHolder(private val binding : HistoryPageBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(term : String){
            binding.recentSearch.text = term
        }
    }
}