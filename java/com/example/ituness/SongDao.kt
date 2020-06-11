package com.example.ituness

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SongDao {

    @Insert
    fun insert(song : Song)

    @Delete
    fun delete(song : Song)

    @Query("SELECT * FROM songs")
    fun  getAllSongs() : LiveData<List<Song>>

    @Query("DELETE FROM songs")
    fun deleteAllSongs()

    @Query("SELECT searched FROM songs")
    fun getRecents() : List<String>

    @Query("SELECT * FROM songs WHERE searched=:term")
    fun getSongs(term : String) : List<Song>

}