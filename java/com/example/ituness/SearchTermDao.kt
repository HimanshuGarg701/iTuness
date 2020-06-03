package com.example.ituness

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface SearchTermDao {

    @Insert
    fun addTerm(term : SearchTerm)

    @Query("SELECT term FROM recents")
    fun getRecents() : List<String>
}