package com.example.ituness

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song(

    @PrimaryKey
    val trackId : Long?,

    @ColumnInfo(name = "song")
    val trackName: String?,

    @ColumnInfo(name = "singer")
    val artistName : String?,

    @ColumnInfo(name="song_preview")
    val previewUrl : String?,

    @ColumnInfo(name = "album")
    val collectionName : String?,

    @ColumnInfo(name = "album_image")
    val artworkUrl100 : String?) {
}