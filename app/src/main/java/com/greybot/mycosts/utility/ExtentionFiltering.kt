package com.greybot.mycosts.utility


fun String?.pathSize(): Int {
    return this?.split("/")?.filter { it.isNotBlank() }?.size ?: 0
}

fun String?.addToPath(text: String?): String {
    val list = this?.split("/")?.filter { it.isNotBlank() }?.toMutableList() ?: mutableListOf()
    text?.let { list.add(it) }
    return list.joinToString("/", prefix = "/")
}

fun getEndSegment(path: String?): String {
    val array = path?.split("/")
    return array?.getOrNull(array.lastIndex) ?: ""
}

fun getNameFromPath(findPath: String, fullPath: String?): String {
    return fullPath?.getNameFromPath2(findPath) ?: ""
}

private fun String.getNameFromPath2(findPath: String): String? {
    return this
        .substring(findPath.length, this.length)
        .split("/")
        .getOrNull(0)
}