package com.greybot.mycosts.data.repository

import com.greybot.mycosts.data.DataSource

class AppRepository(private val source: DataSource = DataSource()) {

    fun getAllFolder(): List<String> {
        return source.exploreList
    }

    fun findFolder(path: String?): List<String> {
        return source.exploreList.filter { it.startsWith(path ?: "") }
    }
}
