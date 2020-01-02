package com.example.testapp.network.model.books

data class BookNetwork(
    val book_image: String,
    val description: String,
    val price: Int,
    val publisher: String,
    val rank: Int,
    val title: String,
    val author: String
)