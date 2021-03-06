package com.example.testapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testapp.network.model.RoomResult
import com.example.testapp.network.model.books.BookRoom
import com.example.testapp.utils.DB_NAME


@Database(entities = [RoomResult::class, BookRoom::class], version = 1, exportSchema = false)
abstract class StoryDatabase : RoomDatabase() {

    companion object {
        private var APPDATABASE: StoryDatabase? = null

        fun getInstance(context: Context): StoryDatabase? {
            if (APPDATABASE == null) {
                synchronized(StoryDatabase::class) {
                    APPDATABASE = Room.databaseBuilder(
                        context.applicationContext,
                        StoryDatabase::class.java, DB_NAME
                    ).build()
                }
            }
            return APPDATABASE
        }
    }

    abstract fun getStoryDao(): StoryDao

    abstract fun getBooksDao(): BooksDao

}