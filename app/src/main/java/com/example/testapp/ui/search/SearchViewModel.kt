package com.example.testapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.example.testapp.database.NewsRepository
import com.example.testapp.network.model.search.Doc
import com.example.testapp.ui.base.BaseViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: NewsRepository) :
    BaseViewModel() {

    val searchLiveData: LiveData<PagedList<Doc>> =
        Transformations.map(repository.searchLiveData) {
            it
        }

    fun getData(query: String) {
        repository.fetchSearchedData(query)
    }
}