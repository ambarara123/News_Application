package com.example.testapp.di

import com.example.testapp.ui.books.BooksFragment
import com.example.testapp.ui.main.NewsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun bindNewsFragment() : NewsFragment

    @ContributesAndroidInjector
    abstract fun bindBooksFragment() : BooksFragment
}