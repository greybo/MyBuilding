package com.greybot.mycosts.base

import com.greybot.mycosts.data.repository.FolderDataSource
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.utility.getNameFromPath

class FindFolderUseCases {

    private val sourceExp: FolderDataSource = FolderDataSource()

    suspend operator fun invoke(
        path: String
    ): MutableSet<AdapterItems>? {
        val findPath = "$path/"
        val list = sourceExp.getFolderAll()
        return list?.mapNotNull { item ->
            if (item.path.startsWith(findPath)) {
                val name = getNameFromPath(findPath, item.path)
                AdapterItems.FolderItem(name, "$findPath$name")
            } else null
        }?.toMutableSet()
    }
}