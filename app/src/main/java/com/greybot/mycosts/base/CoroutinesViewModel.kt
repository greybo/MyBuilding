package com.greybot.mycosts.base

import androidx.lifecycle.viewModelScope
import com.greybot.mycosts.utility.LogApp2
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class CoroutinesViewModel : CompositeViewModel() {

    private val tag = "coroutine_test"
    private val log = LogApp2(tag)
    private var counter = 0
    @OptIn(DelicateCoroutinesApi::class)
    private val singleThreadContext = newSingleThreadContext("Counter")
    private var actorExample = CoroutineActorExample(Dispatchers.Default)

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


    fun actorTest() {
        log.i("actorTest: start")
        viewModelScope.launch(Dispatchers.IO) {
            actorExample = CoroutineActorExample(this.coroutineContext)
            val result = actor(actorExample)
            log.i("actorTest: = $result")
        }
    }

    private suspend fun actor(actorExample: CoroutineActorExample): Int {
        log.thread("actor")
        actorExample.add(256)
        actorExample.remote(56)
        return actorExample.getCounter()
    }


    fun errorTest1() {
        viewModelScope.launch {
            val job = runErrorTest1(this)
            job.invokeOnCompletion { cause: Throwable? ->
                if (cause != null) log.w(cause, "invokeOnCompletion")
                else success()
            }
        }
    }

    private suspend fun runErrorTest1(scope: CoroutineScope = CoroutineScope(Dispatchers.Default)): Job {
        val job = scope.launch(start = CoroutineStart.LAZY) {
            log.i("error test start 1")
            try {
                val error = Response.error<ResponseBody>(400, ResponseBody.create(null, ""))
                throw HttpException(error)
            } catch (e: HttpException) {
                log.e(e, "catch cancel")
                this.cancel(CancellationException(e.message))
            }
        }
        job.join()
        return job
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        log.thread("exceptionHandler")
        log.w(exception, "message: ${exception.message}")
    }

    fun errorTest2(scope: CoroutineScope = CoroutineScope(Dispatchers.Default + exceptionHandler)) {
        log.i("error test start 2")
        scope.launch/*(exceptionHandler)*/ {
            delay(1000)
            log.thread("errorTest2")
            val error: Response<ResponseBody> = Response.error(400, ResponseBody.create(null, ""))
            throw HttpException(error)
        }
    }

    fun errorTest3() {
        log.i("error test start 3")
        scopeDefault.launch {
            delay(1000)
            log.thread("errorTest3")
            throw Throwable("test coroutine exception test 3")
        }
    }

    private fun success() {
        log.i("success")
    }
}
