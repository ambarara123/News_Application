package com.example.testapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.testapp.database.NewsRepository
import com.example.testapp.network.model.RoomResult
import com.example.testapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    BaseViewModel() {

    val storiesLiveData: LiveData<List<RoomResult>> =
        Transformations.map(newsRepository.storiesLiveData) { it }


    val booksLiveData: LiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun getDataCoroutine() {
        viewModelScope.launch {
            newsRepository.fetchStoryDataCoroutine()
        }
    }

}
