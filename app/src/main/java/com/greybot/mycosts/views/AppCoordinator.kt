package com.greybot.mycosts.views

import com.greybot.mycosts.data.repository.folder.FolderDataSource

class AppCoordinator private constructor(){

    companion object {
        lateinit var shared: AppCoordinator
        fun create() {
            shared = AppCoordinator()
        }
    }

    val folderDataSource by lazy { FolderDataSource() }
}