package com.example.ituness

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.ituness.databinding.ActivitySongDetailsBinding
import com.squareup.picasso.Picasso

class SongDetails : AppCompatActivity() {

    private lateinit var binding : ActivitySongDetailsBinding
    private var mp : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_song_details)

        val songName = intent.getStringExtra("songName")
        val previewUrl = intent.getStringExtra("previewUrl")
        val album = intent.getStringExtra("album")
        val image = intent.getStringExtra("image")
        val singer = intent.getStringExtra("singer")

        val song = Song(0, "", songName, singer, previewUrl, album, image)
        assignValues(song)

        if(song.previewUrl!=null) {
            mp = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setDataSource(song.previewUrl)
                prepare()
            }
        }
        binding.playButton.setOnClickListener {
            if(song.previewUrl!=null)
                    mp?.start()
            }

        binding.pauseButton.setOnClickListener {
            mp?.pause()
        }

        binding.stopButton.setOnClickListener {
            mp?.release()
        }
    }

    override fun onPause() {
        super.onPause()
        mp?.release()
    }

    private fun assignValues(song : Song?){
        if(song!=null) {
            if (song.collectionName != null) {
                binding.detailSongAlbum.text = "Album: ${song.collectionName}"
            } else {
                binding.detailSongAlbum.text = "Album: N/A"
            }

            if (song.artistName != null) {
                binding.detailSongSingerName.text = "By: ${song.artistName}"
            } else {
                binding.detailSongSingerName.text = "By: N/A"
            }


            if (song.trackName != null) {
                binding.detailSongName.text = "${song.trackName}"
            } else {
                binding.detailSongName.text = "Song: N/A"
            }

            if (song.artworkUrl100 != null) {
                Picasso.get().load(song.artworkUrl100).into(binding.detailsAlbumImage)
            }
        }
    }
}
