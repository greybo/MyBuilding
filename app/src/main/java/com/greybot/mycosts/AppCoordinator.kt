package com.greybot.mycosts

class AppCoordinator private constructor(){

    companion object {
        lateinit var shared: AppCoordinator
        fun create() {
            shared = AppCoordinator()
        }
    }

//    val folderDataSource by lazy { FolderDataSource() }
//    val rowDataSource by lazy { RowDataSource() }
}