package com.example.testapp

import android.app.Application
import com.example.testapp.di.AppComponent
import com.example.testapp.di.AppModule
import com.example.testapp.di.DaggerAppComponent
import timber.log.Timber

class NewsApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}