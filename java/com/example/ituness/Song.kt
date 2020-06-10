package com.example.ituness

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "songs")
data class Song(

    @PrimaryKey(autoGenerate = true)
    var id : Long = 0L,

    @ColumnInfo(name="searched")
    var searchTerm :String?,

    @ColumnInfo(name = "song")
    val trackName: String?,

    @ColumnInfo(name = "singer")
    val artistName : String?,

    @ColumnInfo(name="song_preview")
    val previewUrl : String?,

    @ColumnInfo(name = "album")
    val collectionName : String?,

    @ColumnInfo(name = "album_image")
    val artworkUrl100 : String?) : Parcelable {

}