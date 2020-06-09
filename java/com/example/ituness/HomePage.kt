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
    private lateinit var songDao : SongDao
    private lateinit var searchDao : SearchTermDao
    private var term : String? = null
    private lateinit var applicationn : Application
    private lateinit var viewModel: HomePageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.recycler_homepage)
        binding.invalidateAll()

        var listOfSongs : List<Song>? = null
        applicationn = requireNotNull(this).application
        val songDao = SongDatabase.getInstance(applicationn).songDao
        val viewModelFactory = HomePageViewModelFactory(songDao, applicationn)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomePageViewModel::class.java)


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
                    term = query
                    term = term?.replace(" ", "+")
                    viewModel.getSongs(term!!)
                    //getAllSongs(term, applicationn)
                    addTerm(term!!)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null) {
                    term = newText
                    term = term?.replace(" ", "+")
                    viewModel.getSongs(term!!)
                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }


    private suspend fun insertSongsToDatabase(application : Application, term : String?, songs : List<Song>){
        withContext(Dispatchers.IO){
            songDao = SongDatabase.getInstance(application).songDao
            songDao.deleteAllSongs()

            for(song in songs) {
                try {
                    Log.d("searchedTermUpdated", term!![term.length-1].toString())
                    song.searchTerm = term
                    songDao.insert(song)
                }
                catch (e: Exception) {
                    continue
                }
            }
        }
    }

    private suspend fun getSongs(application: Application) : List<Song>{
        var results : List<Song> = ArrayList<Song>()
        withContext(Dispatchers.IO){
            songDao = SongDatabase.getInstance(application).songDao
            //results = songDao.getAllSongs()
        }
        return results
    }

    private fun addTerm(term : String){
        coroutineScope.launch {
            insertTermToDatabase(term)
        }
    }

    private suspend fun insertTermToDatabase(term : String){
        withContext(Dispatchers.IO){
            val searchTerm = SearchTerm(UUID.randomUUID().toString(), term)
            searchDao = SearchTermDatabase.getInstance(applicationn).searchTermDao
            searchDao.addTerm(searchTerm)
        }
    }

    private fun getSongsFromDatabase(term : String){
        coroutineScope.launch {
            fetchSongsFromDatabase(term)
        }
    }

    private suspend fun fetchSongsFromDatabase(term : String){
        withContext((Dispatchers.IO)){
            songDao = SongDatabase.getInstance(applicationn).songDao
            val result = songDao.getSongs(term)
            Log.d("NotNullTest", result.toString())
            binding.recyclerSongs.adapter = SongListAdapter(result)
        }
    }
}
