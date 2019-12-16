package com.example.testapp.model

import com.google.gson.annotations.SerializedName

data class NetworkResult(
    @SerializedName("abstract")
    val networkAbstract: String,
    val byline: String,
    val created_date: String,
    val item_type: String,
    val kicker: String,
    val material_type_facet: String,
    val published_date: String,
    val section: String,
    val short_url: String,
    val subsection: String,
    val title: String,
    val updated_date: String,
    val url: String
){

}