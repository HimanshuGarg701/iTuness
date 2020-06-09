package com.example.ituness

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HomePageViewModel (val songDao : SongDao, application: Application) : AndroidViewModel(application) {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    // Fetching the songs through network call
    private fun getSongs(searchTerm : String){

    }

    //Insert song data to Room Database (Cache)
    private fun addSongToDatabase(song : Song){

    }
}