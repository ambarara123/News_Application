package com.example.testapp.di

import com.example.testapp.NewsApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(modules = [AppModule::class, ViewModelModule::class, AndroidInjectionModule::class, ActivityBindingModule::class])
@Singleton
interface AppComponent : AndroidInjector<NewsApplication> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<NewsApplication>
}
