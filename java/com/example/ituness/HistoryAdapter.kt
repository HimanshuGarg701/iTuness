package com.example.ituness

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ituness.databinding.HistoryPageBinding

class HistoryAdapter(private val recents : List<String>) : RecyclerView.Adapter<HistoryAdapter.TermViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HistoryPageBinding.inflate(layoutInflater, parent, false)
        return TermViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recents.size
    }

    override fun onBindViewHolder(holder: TermViewHolder, position: Int) {
        try {
            val recent = recents[position].replace("+", " ").capitalize()
            holder.bind(recent)
            holder.searchTerm = recent
        }catch(e : Exception){
            Log.d("HistoryAdapter", e.message)
        }

    }

    class TermViewHolder(private val binding : HistoryPageBinding, var searchTerm: String?=null) : RecyclerView.ViewHolder(binding.root){

        init{
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, HomePage::class.java)
                intent.putExtra("searchTerm", searchTerm)
                binding.root.context.startActivity(intent)
            }
        }

        fun bind(term : String){
            binding.recentSearch.text = term
        }
    }
}