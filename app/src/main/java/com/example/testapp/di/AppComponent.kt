package com.example.testapp.di

import com.example.testapp.ui.books.BooksFragment
import com.example.testapp.ui.main.MainActivity
import com.example.testapp.ui.main.NewsFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: NewsFragment)
    fun inject(booksFragment: BooksFragment)
}