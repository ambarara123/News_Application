package com.example.testapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testapp.network.model.books.BookRoom

@Dao
interface BooksDao {
    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<BookRoom>

    @Insert
    suspend fun insertBooks(books: List<BookRoom>)

    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()

}