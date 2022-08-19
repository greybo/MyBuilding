package com.greybot.mycosts.base

import com.greybot.mycosts.data.repository.RowDataSource
import com.greybot.mycosts.models.AdapterItems

class RowFolderUseCases {

    private val sourceExp: RowDataSource = RowDataSource()

    suspend operator fun invoke(
        findPath: String
    ): MutableSet<AdapterItems>? {
        val list = sourceExp.getAllData()
        return list?.mapNotNull { item ->
            if (item.path == findPath) {
                AdapterItems.RowItem(item.title, item.path, item.price, item.objectId!!)
            } else null
        }?.toMutableSet()
    }
}