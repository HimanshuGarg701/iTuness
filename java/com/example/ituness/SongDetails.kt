package com.example.ituness

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val song = intent.getParcelableExtra<Song>("SONG")
        if(song==null)
            Log.d("DetailsSong", "null")

        assignValues(song)
        binding.playButton.setOnClickListener {
            if(song!=null && song.previewUrl!=null) {
                mp = MediaPlayer().apply {
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    setDataSource(song.previewUrl)
                    prepare()
                    start()
                }
            }
        }

        binding.pauseButton.setOnClickListener {
            mp?.pause()
        }

        binding.stopButton.setOnClickListener {
            mp?.release()
        }
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
