package com.greybot.mycosts.base

class Path(path: String?) {

    private val _segments = path.splitPath().toMutableList()
    val segments: List<String> get() = _segments.filter { it.isNotBlank() }
    val path get() = segments.joinToString("/", "/")


    fun addToPath(name: String?): String {
        name?.let { _segments.add(it) }
        return segments.joinToString("/", "/")
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Path

        if (segments != other.segments) return false
        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        var result = segments.hashCode()
        result = 31 * result + path.hashCode()
        return result
    }

    override fun toString(): String {
        return path
    }


}

fun String?.splitPath(): List<String> {
    val list = this?.split("/")?.filter { it.isNotBlank() }
    return list ?: emptyList()
}

fun findName(pathFind: String, pathLocal: String): String? {
    val layer = pathFind.splitPath().size
    return pathLocal.splitPath().getOrNull(layer)
}

fun String?.addToPath(text: String?): String {
    return Path(this).addToPath(text)
//    val list = this?.split("/")?.filter { it.isNotBlank() }?.toMutableList() ?: mutableListOf()
//    text?.let { list.add(it) }
//    return list.joinToString("/", prefix = "/")
}

fun getEndSegment(path: String?): String {
    val array = path?.split("/")
    return array?.getOrNull(array.lastIndex) ?: ""
}

//fun String?.pathSize(): Int {
//    return this?.split("/")?.filter { it.isNotBlank() }?.size ?: 0
//}

//fun getNameFromPath(findPath: String, fullPath: String?): String {
//    return fullPath?.getNameFromPath2(findPath) ?: ""
//}
//
//private fun String.getNameFromPath2(findPath: String): String? {
//    return this
//        .substring(findPath.length, this.length)
//        .split("/")
//        .getOrNull(0)
//}
