package com.greybot.mycosts.present.second.add

import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.views.AppCoordinator

class FolderAddViewModel : CompositeViewModel() {

    private val dataSource get() = AppCoordinator.shared.folderDataSource

    fun addFolder(name: String?, path: String?, time: Long) {
        if (name != null)
            dataSource.addFolder(name, path, time)
    }
}