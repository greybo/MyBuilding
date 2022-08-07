package com.greybot.mybuilding.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greybot.mybuilding.utility.LogApp
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*

abstract class CompositeViewModel : ViewModel() {
    @Volatile
    var isCleared: Boolean = false
    val cd = CompositeDisposable()

    override fun onCleared() {
        isCleared = true
        super.onCleared()
        if (!cd.isDisposed) {
            cd.dispose()
        }
    }

    val errorsStream = MutableLiveData<Throwable>()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
//        LogApp.w("CompositeViewModel", exception)
        errorsStream.postValue(exception)
    }

    protected fun launchOn(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(exceptionHandler, block = block)
    }

    protected fun launchOnDefault(block: suspend CoroutineScope.() -> Unit): Job {
        return CoroutineScope(Dispatchers.Default).launch(exceptionHandler, block = block)
    }

    protected fun launchOnIO(block: suspend CoroutineScope.() -> Unit): Job {
        return CoroutineScope(Dispatchers.IO).launch(exceptionHandler, block = block)
    }

    suspend fun withMain(block: suspend CoroutineScope.() -> Unit) {
        return withContext(Dispatchers.Main + exceptionHandler, block)
    }

    suspend fun <T> withDefault(block: suspend CoroutineScope.() -> T): T {
        return withContext(Dispatchers.Default + exceptionHandler, block)
    }

    val scopeDefault get() = CoroutineScope(Dispatchers.Default + exceptionHandler )

    protected fun handleCatch(error: Throwable) {
        LogApp.e("CompositeViewModel", error)
    }
}