package com.greybot.mycosts.utility

fun String?.getTotalString(prefix: String): String {
    return if (this.isNullOrBlank()) "" else "$prefix: $this"
}