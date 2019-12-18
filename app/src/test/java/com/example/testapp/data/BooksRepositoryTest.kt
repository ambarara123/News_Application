package com.example.testapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testapp.model.books.BookRoom
import com.example.testapp.model.books.Response
import com.example.testapp.network.ApiService
import com.example.testapp.utils.API_KEY_BOOKS
import io.reactivex.Maybe
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class BooksRepositoryTest {
    @get:Rule
    val instantExecutorTestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var database: StoryDatabase
    @Mock
    lateinit var apiService: ApiService
    @Mock
    lateinit var observer : Observer<List<BookRoom>>

    lateinit var booksRepository: BooksRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        booksRepository = BooksRepository(apiService,database)

        booksRepository.booksLiveData.observeForever(observer)
    }

    @After
    fun tearDown() {
        booksRepository.onCleared()
    }

    @Test
    fun testNull() = runBlocking {

        //booksRepository.booksLiveData.value = ArgumentMatchers.anyList<BookRoom>()

        assertNotNull(booksRepository.booksLiveData)

        assertTrue(booksRepository.booksLiveData.hasObservers())
    }

    @Test
    fun getBooksLiveData() = runBlocking {
        Mockito.`when`(booksRepository.fetchDataFromNetwork()).thenAnswer {
            return@thenAnswer Maybe.just(ArgumentMatchers.anyList<Response>())
        }

        Mockito.`when`(booksRepository.fetchDataFromRoom()).thenReturn(ArgumentMatchers.anyList<BookRoom>())

        booksRepository.fetchData(ArgumentMatchers.anyBoolean())



    }

    @Test
    fun fetchData() {

    }
}