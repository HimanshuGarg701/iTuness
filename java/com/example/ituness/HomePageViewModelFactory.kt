package com.example.ituness

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class HomePageViewModelFactory (private val songDao : SongDao,
                                private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomePageViewModel::class.java)){
            return HomePageViewModel(
                songDao,
                application
            ) as T
        }
        throw IllegalArgumentException("Failed to create ViewModel")
    }

}