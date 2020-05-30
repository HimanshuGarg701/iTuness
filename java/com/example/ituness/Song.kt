package com.example.ituness

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


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
    val artworkUrl100 : String?) : Parcelable{


    constructor() : this(0, "","","","","")

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(trackId!!)
        parcel.writeString(trackName)
        parcel.writeString(artistName)
        parcel.writeString(previewUrl)
        parcel.writeString(collectionName)
        parcel.writeString(artworkUrl100)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Song> {
        override fun createFromParcel(parcel: Parcel): Song {
            return Song(parcel)
        }

        override fun newArray(size: Int): Array<Song?> {
            return arrayOfNulls(size)
        }
    }
}