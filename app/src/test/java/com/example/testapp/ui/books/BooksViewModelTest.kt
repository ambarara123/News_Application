package com.example.testapp.ui.books

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testapp.database.BooksDao
import com.example.testapp.database.NewsRepository
import com.example.testapp.database.StoryDatabase
import com.example.testapp.network.ApiService
import com.example.testapp.network.model.books.BookRoom
import com.example.testapp.network.model.books.Response
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Maybe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class BooksViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var database: StoryDatabase
    @Mock
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var bookDao: BooksDao

    @Mock
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var newsRepository: NewsRepository
    @Mock
    private lateinit var observer: Observer<List<BookRoom>>

    lateinit var viewModel: BooksViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        newsRepository = NewsRepository(apiService, database, sharedPreferences)
        viewModel = BooksViewModel(newsRepository)
        viewModel.booksLiveData.observeForever(observer)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Test
    fun notNull() {
        assertNotNull(viewModel.booksLiveData)
        assertTrue(viewModel.booksLiveData.hasObservers())
    }

    @Test
    fun getData() {
        runBlocking {

            `when`(newsRepository.fetchBookDataFromNetwork()).thenAnswer {
                return@thenAnswer Maybe.just(ArgumentMatchers.any(Response::class.java))
            }

            `when`(newsRepository.fetchBookDataFromNetwork()).thenAnswer {
                return@thenAnswer Maybe.just(ArgumentMatchers.anyList<Response>())
            }

            `when`(database.getBooksDao()).thenReturn(bookDao)

            `when`(newsRepository.fetchBookDataFromRoom()).thenReturn(ArgumentMatchers.anyList<BookRoom>())

            verify(observer).onChanged(ArgumentMatchers.anyList<BookRoom>())

        }
    }

    @After
    fun clear() {
    }

}