package com.example.testapp.di

import android.content.Context
import com.example.testapp.NewsApplication
import com.example.testapp.ui.books.BooksFragment
import com.example.testapp.ui.main.MainActivity
import com.example.testapp.ui.main.NewsFragment
import com.example.testapp.ui.search.SearchActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [AppModule::class, ViewModelModule::class, AndroidInjectionModule::class, ActivityBindingModule::class])
@Singleton
interface AppComponent : AndroidInjector<NewsApplication> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<NewsApplication>
}
