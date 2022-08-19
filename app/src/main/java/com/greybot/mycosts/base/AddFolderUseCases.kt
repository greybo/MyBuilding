package com.greybot.mycosts.base

import com.greybot.mycosts.data.repository.AppRepository
import com.greybot.mycosts.utility.addToPath
import com.greybot.mycosts.utility.toast

class AddFolderUseCases(private val repo: AppRepository) {

    operator fun invoke(name: String?, path: String?) {
        if (!name.isNullOrBlank()) {
            repo.addNewFolder(name, path.addToPath(name))
        } else toast("name null")
    }
}