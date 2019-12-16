package com.example.testapp.ui.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.testapp.data.BooksRepository

import com.example.testapp.model.books.BookRoom
import com.example.testapp.ui.base.BaseViewModel
import javax.inject.Inject

class BooksViewModel @Inject constructor(val booksRepository: BooksRepository) : BaseViewModel() {

    val booksLiveData : LiveData<List<BookRoom>> =
        Transformations.map(booksRepository.booksLiveData){
            it
        }

    fun getData(isConnected: Boolean){
        booksRepository.fetchData(isConnected)
    }

    override fun onCleared() {
        super.onCleared()
        booksRepository.onCleared()
    }
}