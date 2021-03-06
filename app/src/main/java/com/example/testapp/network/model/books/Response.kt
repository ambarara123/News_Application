package com.example.testapp.network.model.books

data class Response(
    val copyright: String,
    val last_modified: String,
    val num_results: Int,
    val results: Results,
    val status: String
)