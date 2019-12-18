package com.example.testapp.ui.books

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testapp.data.BooksDao
import com.example.testapp.data.BooksRepository
import com.example.testapp.data.StoryDao
import com.example.testapp.data.StoryDatabase
import com.example.testapp.model.books.BookRoom
import com.example.testapp.model.books.Response
import com.example.testapp.network.ApiService
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Maybe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyObject

@RunWith(JUnit4::class)
class BooksViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    lateinit var database: StoryDatabase
    @Mock
    lateinit var apiService: ApiService
    @Mock
    lateinit var bookDao: BooksDao

    lateinit var booksRepository: BooksRepository
    @Mock
    lateinit var observer: Observer<List<BookRoom>>

    lateinit var viewModel: BooksViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        booksRepository = BooksRepository(apiService, database)
        viewModel = BooksViewModel(booksRepository)
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
            `when`(booksRepository.fetchDataFromNetwork()).thenAnswer {
                            return@thenAnswer Maybe.just(ArgumentMatchers.any(Response::class.java))
                        }
            `when`(booksRepository.fetchDataFromNetwork()).thenAnswer {
                return@thenAnswer Maybe.just(ArgumentMatchers.anyList<Response>())
            }

            `when`(database.getBooksDao()).thenReturn(bookDao)

            `when`(booksRepository.fetchDataFromRoom()).thenReturn(ArgumentMatchers.anyList<BookRoom>())

            viewModel.getData(ArgumentMatchers.anyBoolean())

            verify(observer).onChanged(ArgumentMatchers.anyList<BookRoom>())

        }
    }

    @After
    fun clear() {
        booksRepository.onCleared()
    }

}