package com.example.testapp.data

import androidx.paging.ItemKeyedDataSource
import com.example.testapp.model.search.Doc
import com.example.testapp.model.search.SearchResponse
import com.example.testapp.network.ApiService
import com.example.testapp.utils.API_KEY
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchDataSource(
    private val searchQuery: String,
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
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
           val result =  apiService.getSearchedArticle(API_KEY,searchQuery,key)
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
            val result =  apiService.getSearchedArticle(API_KEY,searchQuery,params.key)
            callback.onResult(result.response.docs)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Doc>) {
    }

    override fun getKey(item: Doc): Int {
        return pageNumber++
    }

}