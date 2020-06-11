package com.example.ituness

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

class HomePageViewModel (private val songDao : SongDao, application: Application) : AndroidViewModel(application) {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    var songs = MutableLiveData<List<Song>>()

    init{
        Log.d("ClearedViewMode", "All Started")
        songs.value = ArrayList()
    }

     override fun onCleared() {
        super.onCleared()
        job.cancel()
        Log.d("ClearedViewMode", "All Clear")
    }

    // Fetching the songs through network call
    fun getSongs(searchTerm : String, isSubmitted : Boolean){
        scope.launch {
            songs.value = fetchSongsFromNetwork(searchTerm)
            if(!searchTerm.equals("RecentSearch"))
                addSongToRoomDatabase(songs.value, searchTerm, isSubmitted)
        }
    }

    //Making network call on background thread
    private suspend fun fetchSongsFromNetwork(searchTerm: String) : List<Song>?{
        var returnedSongsData : ReturnedData? = null

        withContext(Dispatchers.IO) {
            if (searchTerm != null && searchTerm != "") {
                val getSongDeferred = SongsApi.retrofitService.getSongs(searchTerm)

                try {
                    returnedSongsData= getSongDeferred.await()
                    Log.d("ReturnedData", returnedSongsData.toString())
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
    private suspend fun addSongToRoomDatabase(listSongs : List<Song>?, searchTerm : String, isSubmitted: Boolean){
        withContext(Dispatchers.IO) {
            if (listSongs != null) {
                for (song in listSongs) {
                    try {
                        if(isSubmitted)
                            song.searchTerm = searchTerm
                        songDao.insert(song)
                    }catch(e : Exception){
                        Log.d("HomePageViewModel", e.message)
                    }
                }
            }
        }
    }

    fun getSongForHistory(term: String){
        scope.launch{
            songs.value = loadSongsFromDatabase(term)
            Log.d("HomePageViewModel", songs.value.toString())
        }
    }

    private suspend fun loadSongsFromDatabase(term : String) : List<Song>?{
        var listOfSongs : List<Song>? = null
        withContext(Dispatchers.IO){
            listOfSongs = songDao.getSongs(term)
        }
        return listOfSongs
    }
}