package com.greybot.mybuilding

import androidx.lifecycle.viewModelScope
import com.greybot.mybuilding.base.CompositeViewModel
import com.greybot.mybuilding.utility.LogCust2
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MainViewModel : CompositeViewModel() {

    private val log = LogCust2("MainViewModel")
    private var counter = 0
    private val lock = Mutex()

    fun runTest() {
        viewModelScope.launch {
            val jobs = List(100) {
                this.launch(start = CoroutineStart.LAZY) {
                    repeat(1_0) {
                        lock.withLock {
                            counter += generateInt()
                            log.i("$counter")
                        }
                    }
                }
            }
            jobs.joinAll()
        }
    }

    private suspend fun generateInt(): Int {
        delay(10)
        return 1
    }
}