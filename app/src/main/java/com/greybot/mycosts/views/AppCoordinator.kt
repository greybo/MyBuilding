package com.greybot.mycosts.views

import com.greybot.mycosts.data.repository.folder.FolderDataSource
import com.greybot.mycosts.data.repository.row.RowDataSource

class AppCoordinator private constructor(){

    companion object {
        lateinit var shared: AppCoordinator
        fun create() {
            shared = AppCoordinator()
        }
    }

    val folderDataSource by lazy { FolderDataSource() }
    val rowDataSource by lazy { RowDataSource() }
}