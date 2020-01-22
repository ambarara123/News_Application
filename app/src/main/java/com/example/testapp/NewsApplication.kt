package com.example.testapp

import com.example.testapp.di.component.AppComponent
import com.example.testapp.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class NewsApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        (applicationInjector() as AppComponent).getActiveNetworkUtil().observeNetworkAvailability()
        Timber.plant(Timber.DebugTree())
    }
}