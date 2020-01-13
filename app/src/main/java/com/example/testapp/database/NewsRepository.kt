package com.example.testapp.database

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.testapp.network.ApiService
import com.example.testapp.network.model.RoomResult
import com.example.testapp.network.model.Stories
import com.example.testapp.network.model.books.BookRoom
import com.example.testapp.network.model.books.Response
import com.example.testapp.network.model.search.Doc
import com.example.testapp.network.model.toRoomResult
import com.example.testapp.utils.API_KEY
import com.example.testapp.utils.API_KEY_BOOKS
import com.example.testapp.utils.IS_CONNECTED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService,
    private val database: StoryDatabase,
    private val sharedPreferences: SharedPreferences
) {

    val storiesLiveData = MutableLiveData<List<RoomResult>>()
    val booksLiveData = MutableLiveData<List<BookRoom>>()

    private val searchQueryLiveData = MutableLiveData<String>()
    private lateinit var sourceFactory: SearchDataSourceFactory

    private val pagedListConfig: PagedList.Config by lazy {
        val pageSize = 10
        PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
    }

    var searchLiveData: LiveData<PagedList<Doc>> = Transformations.switchMap(searchQueryLiveData) {
        sourceFactory = SearchDataSourceFactory(apiService, it ?: "")
        LivePagedListBuilder<Int, Doc>(sourceFactory, pagedListConfig).build()
    }

    private fun isConnected(): Boolean {
        return sharedPreferences.getBoolean(IS_CONNECTED, false)
    }

    suspend fun fetchStoryDataCoroutine() {
        withContext(Dispatchers.IO) {
            if (isConnected()) {
                Timber.d("online mode")
                val stories = fetchStoryDataFromNetworkCoroutine().results.map { it.toRoomResult() }
                deleteAllStories()
                saveStoryDataLocally(stories)
                storiesLiveData.postValue(stories)
            } else {
                Timber.d("offline mode")
                storiesLiveData.postValue(fetchStoryDataFromRoom())
            }
        }
    }

    private suspend fun fetchStoryDataFromNetworkCoroutine(): Stories {
        return apiService.getFromNetworkCoroutine(API_KEY)
    }

    private suspend fun fetchStoryDataFromRoom(): List<RoomResult> {
        return database.getStoryDao().getStories()
    }

    private suspend fun saveStoryDataLocally(result: List<RoomResult>) {
        database.getStoryDao().insertStory(result)
    }

    private suspend fun deleteAllStories() {
        database.getStoryDao()
            .deleteAllStories()
    }

    suspend fun fetchBookData() {
        if (isConnected()) {
            val roomResult = fetchBookDataFromNetwork().results.books.map { it.toRoomResult() }
            deleteAllBooks()
            saveBookDataLocally(roomResult)
            booksLiveData.postValue(roomResult)
        } else {
            booksLiveData.postValue(fetchBookDataFromRoom())
        }

    }

    suspend fun fetchBookDataFromNetwork(): Response {
        return apiService.getBooksDataFromNetwork(API_KEY_BOOKS)
    }

    suspend fun fetchBookDataFromRoom(): List<BookRoom> {
        return database.getBooksDao().getAllBooks()
    }

    private suspend fun saveBookDataLocally(books: List<BookRoom>) {
        database.getBooksDao().insertBooks(books)
    }

    private suspend fun deleteAllBooks() {
        database.getBooksDao()
            .deleteAllBooks()
    }

    fun fetchSearchedData(query: String) {
        searchQueryLiveData.value = query
    }

}