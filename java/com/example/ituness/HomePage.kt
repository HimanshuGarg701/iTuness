package com.example.ituness

import android.app.Application
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ituness.databinding.RecyclerHomepageBinding
import kotlinx.coroutines.*
import javax.security.auth.callback.Callback

class HomePage : AppCompatActivity() {

    private lateinit var binding : RecyclerHomepageBinding
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var songDao : SongDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.recycler_homepage)
        binding.recyclerSongs.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        getAllSongs(requireNotNull(this).application)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    private fun getAllSongs(application : Application){
        coroutineScope.launch {
            var getSongDeferred = SongsApi.retrofitService.getSongs("badshah")

            try{
                val returnedSongsData = getSongDeferred.await()
                insertSongsToDatabase(application, returnedSongsData.results)

                binding.recyclerSongs.adapter = SongListAdapter(getSongs(application))
                Log.d("ReturnedData", returnedSongsData.toString())
            }catch(e : Exception){
                Log.d("ReturnedData", e.message)
            }
        }
    }

    private suspend fun insertSongsToDatabase(application : Application, songs : List<Song>){
        withContext(Dispatchers.IO){
            songDao = SongDatabase.getInstance(application).songDao
            songDao.deleteAllSongs()
            for(song in songs) {
                try {
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
