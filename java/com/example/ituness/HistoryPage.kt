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
    private lateinit var songDao : SongDao
    private lateinit var applicationn : Application
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.recycler_history)
        applicationn = requireNotNull(this.application)
        getSongs()
    }

    private fun getSongs(){
        scope.launch {
            withContext(Dispatchers.IO){
                showList()
            }
        }
    }

    private suspend fun showList(){
        withContext(Dispatchers.IO){
            songDao = SongDatabase.getInstance(applicationn).songDao
            val listSongs = songDao.getAllSongs()
            Log.d("HistoryPage", listSongs.toString())
            binding.recyclerHistory.adapter = HistoryAdapter(listSongs)
        }
    }
}
