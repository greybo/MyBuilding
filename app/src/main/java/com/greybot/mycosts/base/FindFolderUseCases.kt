package com.greybot.mycosts.base

import com.greybot.mycosts.data.repository.AppRepository
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.utility.getNameFromPath

class FindFolderUseCases(private val repo: AppRepository) {

    operator fun invoke(path: String, callback: (MutableSet<AdapterItems>?) -> Unit) {
        val findPath = "$path/"
        repo.getAllFolder { list ->
            callback.invoke(list?.mapNotNull { item ->
                if (item.path.startsWith(findPath)) {
                    val name = item.path.getNameFromPath(findPath)
                    AdapterItems.FolderItem(name, "$findPath$name")
                } else null
            }?.toMutableSet())
        }
    }
}