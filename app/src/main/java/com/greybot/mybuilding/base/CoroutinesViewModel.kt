package com.greybot.mybuilding.base

import androidx.lifecycle.viewModelScope
import com.greybot.mybuilding.utility.LogCust2
import kotlinx.coroutines.*

class CoroutinesViewModel : CompositeViewModel() {

    private val log = LogCust2("MainViewModel")
    private var counter = 0

    private val singleThreadContext = newSingleThreadContext("Counter")

    fun newSingleThreadTest() {
        viewModelScope.launch {
            val jobs = List(100) {
                this.launch(start = CoroutineStart.LAZY) {
                    repeat(1_0) {
                        withContext(singleThreadContext) {
                            counter += generateInt()
                            log.i("$counter")
                        }
                    }
                }
            }
            jobs.joinAll()
            singleThreadContext.close()
        }
    }

    private suspend fun generateInt(): Int {
        delay(10)
        return 1
    }
}