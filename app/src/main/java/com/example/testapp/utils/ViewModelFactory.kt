package com.example.testapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(val creators : Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator : Provider<out ViewModel>? = creators[modelClass]

        if (creator == null){
            for ((key,value) in creators){
                if (modelClass.isAssignableFrom(key)){
                    creator = value
                    break
                }
            }
        }

        if (creator == null){
            throw IllegalArgumentException("unknown class ${modelClass.simpleName}")
        }

        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        }catch (e: Exception){
            throw RuntimeException(e)
        }
    }
}
