package com.example.ituness

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Song::class], version = 20, exportSchema = false)
abstract class SongDatabase : RoomDatabase() {

    abstract val songDao : SongDao

    companion object{
        @Volatile
        private var INSTANCE : SongDatabase? = null

        fun getInstance(context : Context) : SongDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, SongDatabase::class.java, "songs")
                                    .fallbackToDestructiveMigration()
                                    .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}