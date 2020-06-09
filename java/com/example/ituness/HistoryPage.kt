package com.example.ituness

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.ituness.databinding.HomePageBinding
import com.example.ituness.databinding.RecyclerHistoryBinding
import com.example.ituness.databinding.RecyclerHomepageBinding
import kotlinx.coroutines.*

class HistoryPage : AppCompatActivity() {

    private lateinit var binding : RecyclerHistoryBinding
    private lateinit var searchDao : SearchTermDao
    private lateinit var applicationn : Application
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var songDao : SongDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.recycler_history)
        applicationn = requireNotNull(this.application)
        songDao = SongDatabase.getInstance(applicationn).songDao
        getSongs()
    }

    private fun getSongs(){
        scope.launch {
            showList()
        }
    }

    private suspend fun showList(){
        withContext(Dispatchers.IO){
            searchDao = SearchTermDatabase.getInstance(applicationn).searchTermDao
            val listSongs = searchDao.getRecents().toSet().toList()
            Log.d("HistoryPage", listSongs.toString())
            binding.recyclerHistory.adapter = HistoryAdapter(listSongs)
        }
    }
}
