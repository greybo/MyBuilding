package com.greybot.mycosts.utility

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class CustomLiveData<T>(t: T) {
    private val _liveData = MutableLiveData<T>()
    private val liveData: LiveData<T> = _liveData

    init {
        t?.let { _liveData.value = it }
    }

    var value: T = t
        set(v) {
            value(v)
        }

    private fun value(t: T) {
        _liveData.value = t as T
    }

    fun postValue(t: T) {
        _liveData.postValue(t as T)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        liveData.observe(owner, observer)
    }
}

inline fun <reified T> makeLiveData(any: Any? = null): CustomLiveData<T> {
    return CustomLiveData(any as T)
}
