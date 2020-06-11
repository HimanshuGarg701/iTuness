package com.example.ituness

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class HistoryViewModel(private val songDao : SongDao, application: Application) : AndroidViewModel(application) {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    var listTerms = MutableLiveData<List<String>>()

    init{
        try {
            getListOfSearch()
        }catch (e : Exception){
            Log.d("HistoryViewModel", "Failed to load search data")
        }
    }

    private fun getListOfSearch(){
        scope.launch {
            listTerms.value = fetchTermsFromDatabase()?.toSet()?.toList()
            Log.d("listTerms", listTerms.value.toString())
        }
    }

    private suspend fun fetchTermsFromDatabase() : List<String>?{
        var result :List<String>?= null
        withContext(Dispatchers.IO){
            result = songDao.getRecents()
        }
        return result
    }



}