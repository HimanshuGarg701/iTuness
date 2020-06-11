package com.example.ituness

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class HistoryViewModelFactory (private val songDao : SongDao,
                               private val application: Application) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HistoryViewModel::class.java)){
            return HistoryViewModel(
                songDao,
                application
            ) as T
        }
        throw IllegalArgumentException("Failed to create ViewModel")
    }

}