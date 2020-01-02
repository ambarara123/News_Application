package com.example.testapp.ui.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.testapp.database.NewsRepository

import com.example.testapp.network.model.books.BookRoom
import com.example.testapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class BooksViewModel @Inject constructor(private val repository: NewsRepository) : BaseViewModel() {

    val booksLiveData: LiveData<List<BookRoom>> =
        Transformations.map(repository.booksLiveData) {
            it
        }

    fun getData() {
        viewModelScope.launch {
            repository.fetchBookData()
        }
    }

}