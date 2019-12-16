package com.example.testapp.di

import com.example.testapp.ui.books.BooksFragment
import com.example.testapp.ui.main.MainActivity
import com.example.testapp.ui.main.NewsFragment
import com.example.testapp.ui.search.SearchActivity
import com.example.testapp.ui.search.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: NewsFragment)
    fun inject(booksFragment: BooksFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(searchActivity: SearchActivity)
}