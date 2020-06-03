package com.example.ituness

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SearchTerm::class], version = 3, exportSchema = false)
abstract class SearchTermDatabase : RoomDatabase() {

    abstract val searchTermDao : SearchTermDao

    companion object{

        @Volatile
        private var INSTANCE : SearchTermDatabase? = null

        fun getInstance(context : Context) : SearchTermDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance==null){
                    instance = Room.databaseBuilder(context.applicationContext, SearchTermDatabase::class.java, "recents")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}