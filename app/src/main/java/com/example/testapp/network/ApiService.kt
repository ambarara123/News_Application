package com.example.testapp.network

import com.example.testapp.model.Stories
import com.example.testapp.model.books.Response
import com.example.testapp.model.search.SearchResponse
import com.example.testapp.utils.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(STORY_URL)
    fun getDataFromNetwork(@Query(KEY_QUERY) apiKey: String): Observable<Stories>

    @GET(BOOKS_URL)
    suspend fun getBooksDataFromNetwork(@Query(KEY_QUERY) apiKey: String): Response

    @GET(SEARCH_URL)
    suspend fun getSearchedArticle(
        @Query(KEY_QUERY) apiKey: String, @Query(QUERY) query: String, @Query(
            PAGE
        ) page: Int
    ): SearchResponse

    @GET(STORY_URL)
    suspend fun getFromNetworkCoroutine(@Query(KEY_QUERY) apiKey: String): Stories

}
