package com.example.testapp.ui.main

import androidx.lifecycle.*
import com.example.testapp.data.StoryDatabase
import com.example.testapp.data.StoryRepository
import com.example.testapp.model.*
import com.example.testapp.network.ApiService
import com.example.testapp.ui.base.BaseViewModel
import com.example.testapp.utils.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(val storyRepository: StoryRepository) : BaseViewModel() {

    val storiesLiveData: LiveData<List<RoomResult>> =
        Transformations.map(storyRepository.storiesLiveData) { it }

    fun getData(isConnected: Boolean) {
        storyRepository.fetchData(isConnected)
    }

    /*
    * 1. CoroutineScope
    * 2. CoroutineContext
    * 3. Job
    */

    fun getDataCoroutine(isConnected: Boolean){

        val job = viewModelScope.launch {
            storyRepository.fetchDataCoroutine(isConnected)
        }
    }

    override fun onCleared() {
        super.onCleared()
        storyRepository.onCleared()
    }

}
