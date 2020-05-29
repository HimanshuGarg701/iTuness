package com.example.ituness

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.ituness.databinding.RecyclerHomepageBinding

class HomePage : AppCompatActivity() {

    private lateinit var binding : RecyclerHomepageBinding

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }
}
