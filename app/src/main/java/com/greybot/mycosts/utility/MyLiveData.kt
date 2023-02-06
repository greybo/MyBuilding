package com.greybot.mycosts.utility

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> makeLiveData(): MyLiveData<T> {
    return MyLiveData()
}

class MyLiveData<T> {
    private val _state = MutableLiveData<T>()

    var values: T
        get() = _state.value as T
        set(v) {
            _state.value = v as T
        }

    fun postValue(v: T) {
        Handler(Looper.getMainLooper()).post {
            _state.postValue(v as T)
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        _state.observe(owner, observer)
    }
}

