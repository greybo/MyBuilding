package com.greybot.mybuilding.data.repository

class AppRepository() {

    fun getAllFolder(): List<String> {
        return listOf(
            "my building order",
            "my building order/потолок",
            "my building order/пол",
            "my building order/ванна",
            "mama order",
            "mama order/потолок",
            "mama order/пол",
            "mama order/ванна",
            )
    }
}