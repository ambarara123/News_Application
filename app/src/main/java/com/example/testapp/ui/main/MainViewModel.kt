package com.example.testapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.StoryRepository
import com.example.testapp.model.RoomResult
import com.example.testapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(val storyRepository: StoryRepository) : BaseViewModel() {

    val storiesLiveData: LiveData<List<RoomResult>> =
        Transformations.map(storyRepository.storiesLiveData) { it }

    fun getData(isConnected: Boolean) {
        viewModelScope.launch {
            storyRepository.fetchData(isConnected)
        }
    }

    /*
    * 1. CoroutineScope
    * 2. CoroutineContext
    * 3. Job
    */

    fun getDataCoroutine(isConnected: Boolean) {

        val job = viewModelScope.launch {
            storyRepository.fetchDataCoroutine(isConnected)
        }
    }

    override fun onCleared() {
        super.onCleared()
        storyRepository.onCleared()
    }

}
