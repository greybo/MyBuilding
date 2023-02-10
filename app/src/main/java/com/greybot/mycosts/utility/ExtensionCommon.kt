package com.greybot.mycosts.utility

import android.text.Editable

fun String?.getTotalString(prefix: String): String {
    return if (this.isNullOrBlank()) "" else "$prefix: $this"
}

fun Double.round2String(): String {
    return try {
        String.format("%.2f", (this.toFloat())).replace(",", ".")
    } catch (e: Throwable) {
        LogApp.e("try roundTwo", e)
        "0.0"
    }
}

fun Editable.round2Double(): Double? {
    return try {
        String.format("%.2f", this.toString()).replace(",", ".").toDouble()
    } catch (e: Throwable) {
        LogApp.e("try roundTwo", e)
        null
    }
}

fun Float.round2String(): String? {
    return try {
        String.format("%.2f", this).replace(",", ".")
    } catch (e: Throwable) {
        LogApp.e("try roundTwo", e)
        null
    }
}

fun Float.round2Double(): Double {
    return try {
        String.format("%.2f", this).replace(",", ".").toDouble()
    } catch (e: Throwable) {
        LogApp.e("try roundTwo", e)
        0.0
    }
}

fun String.roundToFlow(): Float {
    return try {
        String.format("%.2f", this).replace(",", ".").toFloat()
    } catch (e: Throwable) {
        LogApp.e("try roundTwo", e)
        0F
    }
}

fun Float.roundToString(): String {
    return try {
        String.format("%.2f", this).replace(",", ".")
    } catch (e: Throwable) {
        LogApp.e("try roundTwo", e)
        "0.0"
    }
}