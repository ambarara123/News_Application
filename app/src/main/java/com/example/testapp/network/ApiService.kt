package com.example.testapp.network

import com.example.testapp.model.Stories
import com.example.testapp.model.books.Response
import com.example.testapp.model.search.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("topstories/v2/science.json")
    fun getDataFromNetwork(@Query("api-key") apiKey : String) : Observable<Stories>

    @GET("books/v3/lists/current/hardcover-fiction.json")
    fun getBooksDataFromNetwork(@Query("api-key") apiKey: String) : Observable<Response>

    @GET("search/v2/articlesearch.json")
    fun getSearchedArticle(@Query("api-key") apiKey: String, @Query("q") query : String,@Query("page")page : Int) : Observable<SearchResponse>
}