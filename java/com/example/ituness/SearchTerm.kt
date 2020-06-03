package com.example.ituness

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recents")
data class SearchTerm(

    @PrimaryKey
    val id : String,

    @ColumnInfo(name = "term")
    val term : String) {

}