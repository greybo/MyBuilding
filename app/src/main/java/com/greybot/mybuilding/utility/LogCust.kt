package com.greybot.mybuilding.utility

import android.util.Log
import com.greybot.mybuilding.BuildConfig
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "My_APP"

class LogCust2(_tag: String? = "LogCustom") {
    private val tag = listOf(TAG, _tag).joinToString( "_")
    private val isDebug get() = BuildConfig.BUILD_TYPE == "debug"

    fun i(vararg message: String?) {
        if (isDebug) Log.i(tag, message.joinToString(separator = ", ") ?: "")
    }

    fun e(message: String?, e: Throwable? = Throwable("null")) {
        if (isDebug) Log.e(tag, message ?: "", e)

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

object LogCust {

    private val isDebug get() = BuildConfig.BUILD_TYPE == "debug"

    fun i(tag: String, vararg message: String?) {
        if (isDebug) Timber.tag(TAG + tag).i(message.joinToString(": "))
    }

    fun e(tag: String, message: String?, e: Throwable? = Throwable("empty")) {
        if (isDebug) Timber.tag(TAG + tag).e(e, message ?: "")

    }

    fun w(tag: String, message: String?, e: Throwable? = Throwable("empty")) {
        if (isDebug) Timber.tag(TAG + tag).e(e, message ?: "")
    }
}