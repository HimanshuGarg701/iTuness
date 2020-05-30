package com.example.ituness

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.ituness.databinding.RecyclerHomepageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.security.auth.callback.Callback

class HomePage : AppCompatActivity() {

    private lateinit var binding : RecyclerHomepageBinding
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.recycler_homepage)

//        var mp : MediaPlayer? = null
//        binding.start.setOnClickListener {
//            mp = MediaPlayer().apply {
//                setAudioStreamType(AudioManager.STREAM_MUSIC)
//                setDataSource("https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview111/v4/90/6d/12/906d12eb-3f20-a41b-e07b-e19194b722da/mzaf_768312763704820908.plus.aac.p.m4a" )
//                prepare()
//                start()
//            }
//        }
//
//        binding.stop.setOnClickListener {
//            mp?.release()
//        }

        getAllSongs()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    private fun getAllSongs(){
        coroutineScope.launch {
            var getSongDeferred = SongsApi.retrofitService.getSongs()
            try{
                val returnedSongsData = getSongDeferred.await()
                Log.d("ReturnedData", returnedSongsData.toString())
            }catch(e : Exception){
                Log.d("ReturnedData", e.message)
            }
        }
    }
}
