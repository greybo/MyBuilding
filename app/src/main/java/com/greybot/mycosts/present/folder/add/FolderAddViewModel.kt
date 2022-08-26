package com.greybot.mycosts.present.folder.add

import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.repository.folder.FolderDataSource

class FolderAddViewModel : CompositeViewModel() {

    private val folderAddUseCase get() = FolderDataSource()

    fun addFolder(name: String?, path: String?) {
        folderAddUseCase.addFolder(name, path)
    }
}