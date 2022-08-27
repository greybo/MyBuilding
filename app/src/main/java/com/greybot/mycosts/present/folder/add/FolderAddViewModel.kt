package com.greybot.mycosts.present.folder.add

import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.repository.folder.FolderDataSource

class FolderAddViewModel : CompositeViewModel() {

    private val dataSource get() = FolderDataSource()

    fun addFolder(name: String?, path: String?) {
        dataSource.addFolder(name, path)
    }
}