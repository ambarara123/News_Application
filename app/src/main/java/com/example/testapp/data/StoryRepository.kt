package com.example.testapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.testapp.model.RoomResult
import com.example.testapp.model.Stories
import com.example.testapp.model.toRoomResult
import com.example.testapp.network.ApiService
import com.example.testapp.utils.API_KEY
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


class StoryRepository @Inject constructor(
    private val apiService: ApiService,
    private val database: StoryDatabase
) : BaseRepository() {

    val storiesLiveData = MutableLiveData<List<RoomResult>>()

    fun fetchData(isConnected: Boolean) {
        if (isConnected) {
            addDisposable(
                fetchDataFromNetwork()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ stories ->
                        val roomResults = stories.results.map { it.toRoomResult() }
                        deleteAllStories()
                        saveDataLocally(roomResults)
                        storiesLiveData.value = roomResults
                    }, { error ->
                        Timber.e(error)
                    })
            )
        } else {
            storiesLiveData.value = fetchDataFromRoom()
        }
    }

    private fun fetchDataFromNetwork(): Observable<Stories> {
        return apiService.getDataFromNetwork(API_KEY)
    }

    private fun fetchDataFromRoom(): List<RoomResult> {
        return database.getStoryDao().getStories()
    }

    private fun saveDataLocally(result: List<RoomResult>) {
        database.getStoryDao().insertStory(result)
    }

    private fun deleteAllStories() {
        database.getStoryDao()
            .deleteAllStories()
        Timber.d("Deleted")
    }
}