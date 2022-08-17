package com.greybot.mybuilding.data.repository

class AppRepository() {

    fun getAllFolder(): List<String> {
        return fakeData
    }

    fun findFolder(path: String?): List<String> {
        return fakeData.filter { it.startsWith(path ?: "") }
    }
}

val fakeData = listOf(
    "my building order",
    "my building order/потолок",
    "my building order/пол",
    "my building order/ванна",
    "mama order",
    "mama order/потолок",
    "mama order/пол",
    "mama order/ванна",
)