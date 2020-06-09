package com.example.ituness

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class HomePageViewModel (val songDao : SongDao, application: Application) : AndroidViewModel(application) {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    var songs = MutableLiveData<List<Song>>()

    init{
        songs.value = ArrayList()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    // Fetching the songs through network call
    fun getSongs(searchTerm : String){
        scope.launch {
            songs.value = fetchSongsFromNetwork(searchTerm)
            addSongToRoomDatabase(songs.value, searchTerm)
        }
    }

    //Making network call on background thread
    private suspend fun fetchSongsFromNetwork(searchTerm: String) : List<Song>?{
        var returnedSongsData :ReturnedData? = null

        withContext(Dispatchers.IO) {
            if (searchTerm != null && searchTerm != "") {
                val getSongDeferred = SongsApi.retrofitService.getSongs(searchTerm)

                try {
                    returnedSongsData= getSongDeferred.await()

                } catch (e: Exception) {
                    Log.d("ReturnedData", e.message)
                }
            } else {
                Log.d("TermData", "term is null")
            }
        }
        return returnedSongsData?.results
    }

    //Insert song data to Room Database (Cache)
    private fun addSongToRoomDatabase(listSongs : List<Song>?, searchTerm : String){

    }
}