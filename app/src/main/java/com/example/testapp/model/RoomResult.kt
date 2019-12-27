package com.example.testapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "result")
data class RoomResult(
    val abs: String,
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
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
