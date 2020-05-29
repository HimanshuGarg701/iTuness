package com.example.ituness

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
    fun  getAllSongs()

    @Query("DELETE FROM songs")
    fun deleteAllSongs()

}