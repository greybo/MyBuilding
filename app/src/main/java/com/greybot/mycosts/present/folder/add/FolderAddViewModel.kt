package com.greybot.mycosts.present.folder.add

import com.greybot.mycosts.base.AddFolderUseCases
import com.greybot.mycosts.base.CompositeViewModel

class FolderAddViewModel : CompositeViewModel() {

    private val folderAddUseCase get() = AddFolderUseCases()

    fun addFolder(name: String?, path: String?) {
        folderAddUseCase.invoke(name, path)
    }
}