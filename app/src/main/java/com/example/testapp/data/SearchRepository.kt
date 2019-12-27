package com.example.testapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.testapp.model.search.Doc
import com.example.testapp.network.ApiService
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiService: ApiService) : BaseRepository() {

    private val pageSize = 10

    private val pagedListConfig: PagedList.Config by lazy {
        PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
    }

    private val searchQueryLiveData = MutableLiveData<String>()

    private lateinit var sourceFactory: SearchDataSourceFactory

    var searchLiveData: LiveData<PagedList<Doc>> = Transformations.switchMap(searchQueryLiveData) {
        sourceFactory = SearchDataSourceFactory(apiService, it ?: "")
        LivePagedListBuilder<Int, Doc>(sourceFactory, pagedListConfig).build()
    }

    fun fetchData(query: String) {
        searchQueryLiveData.value = query
    }

}