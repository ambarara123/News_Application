package com.example.testapp.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testapp.model.RoomResult
import com.example.testapp.model.books.BookRoom
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StoryDatabaseAndroidTest {

    private lateinit var storyDao: StoryDao
    private lateinit var booksDao: BooksDao
    private lateinit var database: StoryDatabase

    @Before
    fun setUp() = runBlocking {
            val context = ApplicationProvider.getApplicationContext<Context>()
            database = Room.inMemoryDatabaseBuilder(context, StoryDatabase::class.java).build()
            storyDao = database.getStoryDao()
            booksDao = database.getBooksDao()
            insertFakeData()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertBookData() = runBlocking {
        assert(booksDao.getAllBooks().isNotEmpty())
    }

    private suspend fun insertFakeData() {
        val arrayList = ArrayList<BookRoom>()
        val roomResult =
            BookRoom("ajdbsjkd", "Fakename", 100, "ISBN", 1, "Fake Title", "Fake Author")
        val roomResult2 =
            BookRoom("ajdbsjkd", "Fakename2", 101, "ISBN2", 2, "Fake Title2", "Fake Author2")
        arrayList.add(roomResult)
        arrayList.add(roomResult2)

        booksDao.insertBooks(arrayList)
    }

    @Test
    fun getBookData() = runBlocking {
        val title = booksDao.getAllBooks().get(0).title
        assertEquals("Fake Title",title)
    }

    @Test
    fun deleteData() = runBlocking {
        booksDao.deleteAllBooks()
        assert(booksDao.getAllBooks().isEmpty())
    }

    @Test
    fun getBooksDao() {

    }
}