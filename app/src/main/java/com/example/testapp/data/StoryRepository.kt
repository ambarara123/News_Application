package com.example.testapp.data

import androidx.lifecycle.MutableLiveData
import com.example.testapp.model.RoomResult
import com.example.testapp.model.Stories
import com.example.testapp.model.toRoomResult
import com.example.testapp.network.ApiService
import com.example.testapp.utils.API_KEY
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


class StoryRepository @Inject constructor(
    private val apiService: ApiService,
    private val database: StoryDatabase
) : BaseRepository() {

    val storiesLiveData = MutableLiveData<List<RoomResult>>()

    suspend fun fetchData(isConnected: Boolean) {
        if (isConnected) {
            addDisposable(
                fetchDataFromNetwork()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ stories ->
                        val roomResults = stories.results.map { it.toRoomResult() }
                        // deleteAllStories()
                        // saveDataLocally(roomResults)
                        storiesLiveData.value = roomResults
                    }, { error ->
                        Timber.e(error)
                    })
            )
        } else {

            storiesLiveData.value = fetchDataFromRoom()
        }
    }

    suspend fun fetchDataCoroutine(isConnected: Boolean) {
        withContext(Dispatchers.IO) {
            if (isConnected) {
                val stories = fetchDataFromNetworkCoroutine().results.map { it.toRoomResult() }
                deleteAllStories()
                saveDataLocally(stories)
                storiesLiveData.postValue(stories)
            } else {
                storiesLiveData.postValue(fetchDataFromRoom())
            }
        }
    }

    private fun fetchDataFromNetwork(): Observable<Stories> {
        return apiService.getDataFromNetwork(API_KEY)
    }

    private suspend fun fetchDataFromNetworkCoroutine(): Stories {
        return apiService.getFromNetworkCoroutine(API_KEY)
    }

    private suspend fun fetchDataFromRoom(): List<RoomResult> {
        return database.getStoryDao().getStories()
    }

    private suspend fun saveDataLocally(result: List<RoomResult>) {
        database.getStoryDao().insertStory(result)
    }

    private suspend fun deleteAllStories() {
        database.getStoryDao()
            .deleteAllStories()
        Timber.d("Deleted")
    }
}