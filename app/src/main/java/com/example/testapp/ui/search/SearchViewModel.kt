package com.example.testapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.example.testapp.data.SearchRepository
import com.example.testapp.model.search.Doc
import com.example.testapp.ui.base.BaseViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor(val searchRepository: SearchRepository) :
    BaseViewModel() {

    val searchLivedata: LiveData<PagedList<Doc>> =
        Transformations.map(searchRepository.searchLiveData) {
            it
        }

    fun getData(query: String) {
        searchRepository.fetchData(query)
    }

    override fun onCleared() {
        super.onCleared()
        searchRepository.onCleared()
    }
}