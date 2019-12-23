package com.example.testapp.data

import androidx.lifecycle.MutableLiveData
import com.example.testapp.model.Stories
import com.example.testapp.model.books.BookRoom
import com.example.testapp.model.books.Response
import com.example.testapp.model.toRoomResult
import com.example.testapp.network.ApiService
import com.example.testapp.utils.API_KEY
import com.example.testapp.utils.API_KEY_BOOKS
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class BooksRepository @Inject constructor(
    private val apiService: ApiService,
    private val database: StoryDatabase
) : BaseRepository() {
    val booksLiveData = MutableLiveData<List<BookRoom>>()

    suspend fun fetchData(isConnected : Boolean){
        withContext(Dispatchers.IO) {
            if (isConnected) {
                val roomResult = fetchDataFromNetwork().results.books.map { it.toRoomResult() }
                deleteAllBooks()
                saveDataLocally(roomResult)
                booksLiveData.postValue(roomResult)
            } else {
                booksLiveData.postValue(fetchDataFromRoom())
            }
        }
    }

     suspend fun fetchDataFromNetwork(): Response {
         Timber.d("Books fetched from network")
        return apiService.getBooksDataFromNetwork(API_KEY_BOOKS)
    }



     suspend fun fetchDataFromRoom(): List<BookRoom> {
         Timber.d("Books fetched from room")
        return database.getBooksDao().getAllBooks()

    }

    private suspend fun saveDataLocally(books: List<BookRoom>) {
        database.getBooksDao().insertBooks(books)
        Timber.d("Books Saved locally")
    }

    private suspend fun deleteAllBooks() {
        database.getBooksDao()
            .deleteAllBooks()
        Timber.d("Books Deleted")
    }

}