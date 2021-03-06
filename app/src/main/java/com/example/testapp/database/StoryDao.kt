package com.example.testapp.database

import androidx.room.*
import com.example.testapp.network.model.RoomResult

@Dao
interface StoryDao {

    @Query("SELECT * FROM result")
    suspend fun getStories(): List<RoomResult>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateStory(result: RoomResult)

    @Insert
    suspend fun insertStory(result: List<RoomResult>)

    @Delete
    suspend fun deleteStory(result: RoomResult)

    @Query("DELETE FROM result")
    suspend fun deleteAllStories()

}