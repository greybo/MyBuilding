package com.greybot.mycosts.utility


fun String?.splitPath(): Int {
    return this?.split("/")?.filter { it.isNotBlank() }?.size ?: 0
}