package com.example.ituness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ituness.databinding.RecyclerHistoryBinding

class HistoryPage : AppCompatActivity() {

    private lateinit var viewModel: HomePageViewModel
    private lateinit var binding : RecyclerHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.recycler_history)

        //creating viewModel object
        val applicationn = requireNotNull(this.application)
        val songDao = SongDatabase.getInstance(applicationn).songDao
        val viewModelFactory = HomePageViewModelFactory(songDao, applicationn)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomePageViewModel::class.java)

        //Observing list of recent searches
        viewModel.listTerms.observe(this, Observer{recents ->
            Log.d("Recrents", recents.toString())
            binding.recyclerHistory.adapter = HistoryAdapter(recents)
        })
    }
}
