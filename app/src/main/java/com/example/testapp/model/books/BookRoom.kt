package com.example.testapp.model.books

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookRoom(
    val book_image: String,
    val description: String,
    val price: Int,
    val publisher: String,
    val rank: Int,
    val title: String,
    val author: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}