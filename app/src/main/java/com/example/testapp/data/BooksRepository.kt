package com.example.testapp.data

import androidx.lifecycle.MutableLiveData
import com.example.testapp.model.books.BookRoom
import com.example.testapp.model.books.Response
import com.example.testapp.model.toRoomResult
import com.example.testapp.network.ApiService
import com.example.testapp.utils.API_KEY_BOOKS
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class BooksRepository @Inject constructor(
    private val apiService: ApiService,
    private val database: StoryDatabase
) : BaseRepository() {
    val booksLiveData = MutableLiveData<List<BookRoom>>()

    fun fetchData(isConnected: Boolean) {
        if (isConnected) {
            addDisposable(
                fetchDataFromNetwork()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        Timber.d(response.results.toString())
                        val roomResults = response.results.books.map { it.toRoomResult() }
                        deleteAllBooks()
                        saveDataLocally(roomResults)
                        booksLiveData.value = roomResults
                    }, { error ->
                        Timber.e(error)
                    })
            )
        } else {
            booksLiveData.value = fetchDataFromRoom()
        }
    }

    private fun fetchDataFromNetwork(): Observable<Response> {
        return apiService.getBooksDataFromNetwork(API_KEY_BOOKS)
    }

    private fun fetchDataFromRoom(): List<BookRoom> {
        return database.getBooksDao().getAllBooks()
    }

    private fun saveDataLocally(books: List<BookRoom>) {
        database.getBooksDao().insertBooks(books)
    }

    private fun deleteAllBooks() {
        database.getBooksDao()
            .deleteAllBooks()
        Timber.d("Books Deleted")
    }

}