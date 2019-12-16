package com.example.testapp.data

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseRepository {

    var compositeDisposable: CompositeDisposable? = CompositeDisposable()

    fun onCleared() {
        if (compositeDisposable != null) {
            compositeDisposable?.dispose()
            compositeDisposable?.clear()
            compositeDisposable = null
        }
    }

    protected fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }
}