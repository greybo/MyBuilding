package com.greybot.mybuilding

import androidx.lifecycle.viewModelScope
import com.greybot.mybuilding.base.CompositeViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.locks.ReentrantLock

class MainViewModel : CompositeViewModel() {

    var counter = 0
    val lock = ReentrantLock()

    suspend fun runTest() {
        viewModelScope.launch {
            val jobs = List(100) {
                this.launch()
            }
        }
    }

}