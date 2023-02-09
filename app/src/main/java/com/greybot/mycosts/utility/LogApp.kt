package com.greybot.mycosts.utility

import android.annotation.SuppressLint
import android.util.Log
import com.greybot.mycosts.BuildConfig
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "My_APP"
private val isDebug get() = BuildConfig.BUILD_TYPE == "debug"

//class LogApp3 {
//    companion object {
//        private var _tag: String? = ""
//        private val tag get() = listOf(TAG, _tag).joinToString("_")
//
//        fun tag(tag: String): Companion {
//            _tag = tag
//            return this
//        }
//
//        fun i(vararg message: String?) {
//            if (isDebug) Log.i(tag, message.joinToString(separator = ", "))
//        }
//
//        fun e(e: Throwable?, message: String? = null) {
////            if (isDebug) Timber.tag(tag).e(e, message)
//            if (isDebug) Log.e(tag, message ?: "", e)
//        }
//
//        fun w(e: Throwable?, message: String? = null) {
////            if (isDebug) Timber.tag(tag).w(e, message)
//            if (isDebug) Log.w(tag, message ?: "", e)
//        }
//    }
//}

@SuppressLint("LogNotTimber")
class LogApp2(private var _tag: String? = "LogCustom") {

    private val tag get() = listOf(TAG, _tag).joinToString("_")
    private val isDebug get() = BuildConfig.BUILD_TYPE == "debug"

    fun tag(tag: String): LogApp2 {
        _tag = tag
        return this
    }

    fun i(vararg message: String?) {
        if (isDebug) Log.i(tag, message.joinToString(separator = ", "))
    }

    fun e(e: Throwable?, message: String? = null) {
        if (isDebug) Log.e(tag, message ?: "", e)
    }

    fun w(e: Throwable?, message: String? = null) {
        if (isDebug) Log.w(tag, message ?: "", e)
    }

    fun thread(message: String) {
        i(message, Thread.currentThread().name)
    }

    private val pattern = "HH:mm:ss.SSS"
    private val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())

    fun time(message: String) {
        val date: String = simpleDateFormat.format(Date(System.currentTimeMillis()))
        Timber.tag(tag).i("$date - $message")
    }
}

object LogApp {

    private val isDebug get() = BuildConfig.BUILD_TYPE == "debug"
    private val log by lazy { LogApp2("") }

    fun i(vararg message:  String?) {
        if (isDebug) log.i(message.joinToString("/", ": "))
    }
    fun d(tag: String, vararg message: String?) {
        if (isDebug) log.tag(tag).i(message.joinToString("/", ": "))
    }

    fun e(tag: String, e: Throwable?, message: String? = null) {
        if (isDebug) log.tag(tag).e(e, message ?: "")
    }

    fun w(tag: String, e: Throwable?, message: String? = null) {
        if (isDebug) log.tag(tag).w(e, message ?: "")
    }

//    private fun getTag(tag: String) = listOf(TAG, tag).joinToString("_")
}