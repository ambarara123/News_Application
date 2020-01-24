package com.example.testapp

import androidx.appcompat.app.AppCompatDelegate
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
        with((applicationInjector() as AppComponent)) {
            getActiveNetworkUtil().observeNetworkAvailability()

            getSharedPreferences().getBoolean(getString(R.string.key_mode), false).let {
                if (it) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }

        Timber.plant(Timber.DebugTree())
    }
}