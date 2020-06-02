package com.example.ituness

import android.app.Application
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ituness.databinding.RecyclerHomepageBinding
import kotlinx.coroutines.*
import java.lang.StringBuilder
import javax.security.auth.callback.Callback

class HomePage : AppCompatActivity() {

    private lateinit var binding : RecyclerHomepageBinding
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var songDao : SongDao
    private var term : String? = null
    private lateinit var applicationn : Application

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.recycler_homepage)
        binding.recyclerSongs.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        applicationn = requireNotNull(this).application
        getAllSongs(term, applicationn)
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
                term = query
                term = term?.replace(" ", "+")
                getAllSongs(term, applicationn)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                term = newText
                term = term?.replace(" ", "+")
                getAllSongs(term, applicationn)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun getAllSongs(term : String?, application : Application){
        coroutineScope.launch {
            if(term!=null && !term.equals("")) {
                var getSongDeferred = SongsApi.retrofitService.getSongs(term)

                try {
                    val returnedSongsData = getSongDeferred.await()

                    insertSongsToDatabase(application, term, returnedSongsData.results)

                    binding.recyclerSongs.adapter = SongListAdapter(getSongs(application))
                    Log.d("ReturnedData", returnedSongsData.toString())
                } catch (e: Exception) {
                    Log.d("ReturnedData", e.message)
                }
            }else{
                Log.d("TermData", "term is null")
            }
        }
    }

    private suspend fun insertSongsToDatabase(application : Application, term : String?, songs : List<Song>){
        withContext(Dispatchers.IO){
            var searchTerm = term
            songDao = SongDatabase.getInstance(application).songDao
            songDao.deleteAllSongs()

            for(song in songs) {
                try {
                    Log.d("searchedTermUpdated", term!![term.length-1].toString())
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
            results = songDao.getAllSongs()
        }
        return results
    }
}
