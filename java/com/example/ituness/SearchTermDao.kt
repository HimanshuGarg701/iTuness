package com.example.ituness

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface SearchTermDao {

    @Insert
    fun addTerm(term : SearchTerm)

    @Query("DELETE FROM recents WHERE id=:id")
    fun deleteTerm(id : Int)

    @Query("SELECT * FROM recents")
    fun getRecents() : List<SearchTerm>
}