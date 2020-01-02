package com.example.testapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testapp.network.ApiService
import com.example.testapp.network.model.books.BookRoom
import com.example.testapp.network.model.books.Response
import io.reactivex.Maybe
import kotlinx.coroutines.runBlocking
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
import org.mockito.Mockito
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

    lateinit var booksRepository: NewsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        booksRepository = NewsRepository(apiService, database)

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
        Mockito.`when`(booksRepository.fetchBookDataFromNetwork()).thenAnswer {
            return@thenAnswer Maybe.just(ArgumentMatchers.anyList<Response>())
        }

        Mockito.`when`(booksRepository.fetchBookDataFromRoom())
            .thenReturn(ArgumentMatchers.anyList<BookRoom>())

        booksRepository.fetchBookData(ArgumentMatchers.anyBoolean())

    }

    @Test
    fun fetchData() {

    }
}