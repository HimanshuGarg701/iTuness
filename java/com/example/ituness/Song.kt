package com.example.ituness

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song(

    @PrimaryKey
    val songName: String,

    @ColumnInfo(name = "singer")
    val singerName : String,

    @ColumnInfo(name = "album")
    val albumName : String,

    @ColumnInfo(name = "album_image")
    val imageUrl : String) {
}