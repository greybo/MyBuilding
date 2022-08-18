package com.greybot.mycosts.utility


fun String?.pathSize(): Int {
    return this?.split("/")?.filter { it.isNotBlank() }?.size ?: 0
}

fun String?.addToPath(text: String?): String {
    val list = this?.split("/")?.toMutableList() ?: mutableListOf()
    text?.let { list.add(it) }
    return list.joinToString("/", prefix = "/")
}

fun String?.getNameFromPath(findPath: String): String {
    return this
        ?.replace(findPath, "")
        ?.split("/")
        ?.getOrNull(0) ?: ""
}