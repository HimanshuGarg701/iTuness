package com.example.ituness

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.ituness.databinding.HomePageBinding
import com.example.ituness.databinding.RecyclerHistoryBinding
import com.example.ituness.databinding.RecyclerHomepageBinding
import kotlinx.coroutines.*

class HistoryPage : AppCompatActivity() {

    private lateinit var viewModel: HomePageViewModel
    private lateinit var binding : RecyclerHistoryBinding
    private lateinit var searchDao : SearchTermDao
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.recycler_history)

        //creating viewModel object
        val applicationn = requireNotNull(this.application)
        val songDao = SongDatabase.getInstance(applicationn).songDao
        val viewModelFactory = HomePageViewModelFactory(songDao, applicationn)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomePageViewModel::class.java)
        getSongs()
    }

    private fun getSongs(){

    }
}
