package com.example.testapp.database

import androidx.paging.ItemKeyedDataSource
import com.example.testapp.network.ApiService
import com.example.testapp.network.model.search.Doc
import com.example.testapp.utils.API_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchDataSource(
    private val searchQuery: String,
    private val apiService: ApiService
) : ItemKeyedDataSource<Int, Doc>() {

    private val networkScope = CoroutineScope(Dispatchers.IO)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Doc>
    ) {
        val key = params.requestedInitialKey ?: 1

        networkScope.launch {
            val result = apiService.getSearchedArticle(API_KEY, searchQuery, key)
            callback.onResult(result.response.docs)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Doc>) {
        networkScope.launch {
            val result = apiService.getSearchedArticle(API_KEY, searchQuery, params.key)
            callback.onResult(result.response.docs)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Doc>) {
    }

    override fun getKey(item: Doc): Int {
        var pageNumber = 1
        return pageNumber++
    }

}