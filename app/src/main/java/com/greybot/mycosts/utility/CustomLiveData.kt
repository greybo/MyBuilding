package com.greybot.mycosts.utility

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

@Suppress("unused")
class CustomLiveData<T>(t: T) {
    private val _liveData = MutableLiveData<T>()

    init {
        t?.let { _liveData.value = it }
    }

    var values: T = t
        set(v) {
            field = v
            (v).also { _liveData.value = it }
        }

    fun postValue(t: T) {
        Handler(Looper.getMainLooper()).post {
            with(_liveData) { postValue(t) }
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        _liveData.observe(owner, observer)
    }
}

inline fun <reified T> makeLiveData(any: Any? = null): CustomLiveData<T> {
    return CustomLiveData(any as T)
}
