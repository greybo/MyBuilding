package com.greybot.mycosts.utility

fun formatPathFolder(path: String, name: String): String {
    return path.split("/").toMutableList().also { list ->
        list.add(name)
    }.filter { it.isNotBlank() }.joinToString("/", "/")
}
