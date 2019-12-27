package com.example.testapp.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.testapp.model.search.Doc
import com.example.testapp.network.ApiService

class SearchDataSourceFactory(private val apiService: ApiService, private val query: String) :
    DataSource.Factory<Int, Doc>() {

    private val searchLiveData = MutableLiveData<SearchDataSource>()

    override fun create(): DataSource<Int, Doc> {
        val dataSource = SearchDataSource(query, apiService)
        searchLiveData.postValue(dataSource)
        return dataSource
    }
}