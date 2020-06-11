package com.example.ituness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.ituness.databinding.RecyclerHistoryBinding

class HistoryPage : AppCompatActivity() {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var binding : RecyclerHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.recycler_history)

        //creating viewModel object
        val applicationn = requireNotNull(this.application)
        val songDao = SongDatabase.getInstance(applicationn).songDao
        val viewModelFactory = HistoryViewModelFactory(songDao, applicationn)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HistoryViewModel::class.java)


        var listOfTerms : List<String>? =null


        //Observing list of recent searches
        viewModel.listTerms.observe(this, Observer{recents ->
            Log.d("Recents", recents.toString())
            listOfTerms = recents
            binding.recyclerHistory.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
            if(listOfTerms!=null)
                binding.recyclerHistory.adapter = HistoryAdapter(listOfTerms!!)
        })
    }
}
