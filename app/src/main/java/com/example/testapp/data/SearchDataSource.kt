package com.example.testapp.data

import androidx.paging.ItemKeyedDataSource
import com.example.testapp.model.search.Doc
import com.example.testapp.network.ApiService
import com.example.testapp.utils.API_KEY
import kotlinx.coroutines.*

class SearchDataSource(
    private val searchQuery: String,
    private val apiService: ApiService

) : ItemKeyedDataSource<Int, Doc>() {

    private var pageNumber = 1

    private val networkScope = CoroutineScope(Dispatchers.IO)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Doc>
    ) {
        val key = params.requestedInitialKey ?: 1

//        compositeDisposable.add(
//            apiService.getSearchedArticle(API_KEY, searchQuery, key)
//                .subscribe {
//                    callback.onResult(it.response.docs)
//                }
//        )

        networkScope.launch {
            val result = apiService.getSearchedArticle(API_KEY, searchQuery, key)
            callback.onResult(result.response.docs)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Doc>) {
        /*compositeDisposable.add(
            apiService.getSearchedArticle(API_KEY, searchQuery, params.key)
                .subscribe {
                    callback.onResult(it.response.docs)
                })*/
        networkScope.launch {
            val result = apiService.getSearchedArticle(API_KEY, searchQuery, params.key)
            callback.onResult(result.response.docs)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Doc>) {
    }

    override fun getKey(item: Doc): Int {
        return pageNumber++
    }

    fun cancelJOb() {
        val cancellationException = CancellationException("cancel all jobs")
        networkScope.cancel(cancellationException)
    }

}