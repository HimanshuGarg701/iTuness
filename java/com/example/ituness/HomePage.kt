package com.example.ituness

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
import androidx.lifecycle.Observer

class HomePage : AppCompatActivity() {

    private lateinit var binding : RecyclerHomepageBinding
    private lateinit var viewModel: HomePageViewModel
    private var listOfSongs : List<Song>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.recycler_homepage
        )
        binding.invalidateAll()


        //creating viewModel object
        val applicationn = requireNotNull(this).application
        val songDao = SongDatabase.getInstance(
            applicationn
        ).songDao
        val viewModelFactory =
            HomePageViewModelFactory(
                songDao,
                applicationn
            )
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomePageViewModel::class.java)


        try {
            val termReceived = intent.getStringExtra("searchTerm")
            binding.recyclerSongs.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

            if (termReceived != null) {
                viewModel.getSongForHistory(termReceived.replace(" ", "+"))
                viewModel.songs.observe(this, Observer{newList ->
                    listOfSongs = newList

                    if(listOfSongs!=null && listOfSongs!!.isNotEmpty())
                        binding.recyclerSongs.adapter =
                            SongListAdapter(
                                listOfSongs!!
                            )
                })
            }else{
                //Observing live data (list of songs) from viewModel
                viewModel.songs.observe(this, Observer{newList ->
                    listOfSongs = newList

                    if(listOfSongs!=null && listOfSongs!!.isNotEmpty())
                        binding.recyclerSongs.adapter =
                            SongListAdapter(
                                listOfSongs!!
                            )
                })
            }
        }catch(e: Exception){
            Log.d("LoadFailed", "Failed to load data")
        }

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
                    viewModel.getSongs(query.replace(" ", "+"), true)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null) {
                    try {
                        viewModel.getSongs(newText.replace(" ", "+"), false)
                    }catch(e : Exception){
                        Log.d("HomePageE", e.message)
                    }
                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}
