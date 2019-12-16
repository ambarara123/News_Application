package com.example.testapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testapp.model.RoomResult

@Dao
interface StoryDao {

    @Query("SELECT * FROM result")
    fun getLiveStories(): LiveData<List<RoomResult>>

    @Query("SELECT * FROM result")
    fun getStories(): List<RoomResult>

    @Update
    fun updateStory(result: RoomResult)

    @Insert
    fun insertStory(result: List<RoomResult>)

    @Delete
    fun deleteStory(result: RoomResult)

    @Query("DELETE FROM result")
    fun deleteAllStories()

}