package com.example.ituness

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.ituness.databinding.RecyclerHomepageBinding
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import androidx.lifecycle.Observer

class HomePage : AppCompatActivity() {

    private lateinit var binding : RecyclerHomepageBinding
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var searchDao : SearchTermDao
    private var term : String? = null
    private lateinit var applicationn : Application
    private lateinit var viewModel: HomePageViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.recycler_homepage)
        binding.invalidateAll()

        var listOfSongs : List<Song>? = null

        //creating viewModel object
        applicationn = requireNotNull(this).application
        val songDao = SongDatabase.getInstance(applicationn).songDao
        val viewModelFactory = HomePageViewModelFactory(songDao, applicationn)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomePageViewModel::class.java)


        //Observing live data (list of songs) from viewModel
        viewModel.songs.observe(this, Observer{newList ->
            listOfSongs = newList
            binding.recyclerSongs.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
            if(listOfSongs!=null)
                binding.recyclerSongs.adapter = SongListAdapter(listOfSongs!!)
        })


//        try {
//            val termReceived = intent.getStringExtra("searchTerm")
//            if (termReceived != null) {
//                getSongsFromDatabase(termReceived)
//            }
//        }catch(e: Exception){
//            Log.d("LoadFailed", "Failed to load data")
//        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.recent ->{
                val intent = Intent(this, HistoryPage::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val menuItem = menu?.findItem(R.id.search)
        val searchView = menuItem!!.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null) {
                    viewModel.getSongs(query.replace(" ", "+"))
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null) {
                    viewModel.getSongs(newText.replace(" ", "+"))
                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}
