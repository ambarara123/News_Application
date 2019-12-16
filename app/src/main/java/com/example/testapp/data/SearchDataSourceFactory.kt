package com.example.testapp.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.testapp.model.search.Doc
import com.example.testapp.network.ApiService
import io.reactivex.disposables.CompositeDisposable

class SearchDataSourceFactory(private val apiService: ApiService,private val query : String,private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, Doc>() {

    val searchLiveData = MutableLiveData<SearchDataSource>()
    override fun create(): DataSource<Int, Doc> {
        val dataSource = SearchDataSource(query,apiService,compositeDisposable)
        searchLiveData.postValue(dataSource)
        return dataSource
    }

}