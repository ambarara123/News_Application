package com.example.testapp.network.model


data class Stories(
    val copyright: String,
    val last_updated: String,
    val num_results: Int,
    val results: List<NetworkResult>,
    val section: String,
    val status: String
)