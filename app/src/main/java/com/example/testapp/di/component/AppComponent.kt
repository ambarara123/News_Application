package com.example.testapp.di.component

import com.example.testapp.NewsApplication
import com.example.testapp.di.module.ActivityBindingModule
import com.example.testapp.di.module.AppModule
import com.example.testapp.di.module.ViewModelModule
import com.example.testapp.utils.ActiveNetworkUtil
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(modules = [AppModule::class, ViewModelModule::class, AndroidInjectionModule::class, ActivityBindingModule::class])
@Singleton
interface AppComponent : AndroidInjector<NewsApplication> {

    fun getActiveNetworkUtil(): ActiveNetworkUtil

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<NewsApplication>
}
