package com.greybot.mycosts.base

import com.greybot.mycosts.data.dto.FolderDTO
import com.greybot.mycosts.data.repository.FolderDataSource
import com.greybot.mycosts.utility.addToPath
import com.greybot.mycosts.utility.toast

class AddFolderUseCases {

    private val sourceExp: FolderDataSource by lazy { FolderDataSource() }

    operator fun invoke(name: String?, path: String?) {
        if (!name.isNullOrBlank()) {
            val item = FolderDTO(name = name, path = path.addToPath(name))
            sourceExp.addFolder(item)
        } else toast("name null")
    }
}