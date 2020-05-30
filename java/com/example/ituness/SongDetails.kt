package com.example.ituness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SongDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_details)

        val song = intent.getParcelableExtra<Song>("SONG")
        assignValues(song)
    }

    private fun assignValues(song : Song){
        
    }
}
