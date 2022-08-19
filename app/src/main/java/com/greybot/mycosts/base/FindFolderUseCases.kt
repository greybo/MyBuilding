package com.greybot.mycosts.base

import com.greybot.mycosts.data.dto.FolderDTO
import com.greybot.mycosts.data.repository.FolderDataSource
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.utility.getNameFromPath

class FindFolderUseCases(private val sourceExp: FolderDataSource = FolderDataSource()) {

    operator fun invoke(path: String, callback: (MutableSet<AdapterItems>?) -> Unit) {
        val findPath = "$path/"
        getAllFolder { list ->
            callback.invoke(list?.mapNotNull { item ->
                if (item.path.startsWith(findPath)) {
                    val name = item.path.getNameFromPath(findPath)
                    AdapterItems.FolderItem(name, "$findPath$name")
                } else null
            }?.toMutableSet())
        }
    }

    private fun getAllFolder(callback: (List<FolderDTO>?) -> Unit) {
        sourceExp.getFolderAll {
            callback(it)
        }
    }
}