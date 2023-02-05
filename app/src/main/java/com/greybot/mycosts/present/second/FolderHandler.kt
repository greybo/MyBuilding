package com.greybot.mycosts.present.second

import com.greybot.mycosts.data.dto.ExploreRow
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.second.preview.ButtonType
import com.greybot.mycosts.present.second.preview.ItemTotalHelper
import com.greybot.mycosts.utility.getTotalString

class FolderHandler(private val total: ItemTotalHelper) {
    fun makeFolderItems(list: List<ExploreRow>): List<AdapterItems> {
        return buildList {
            addAll(list.map { f ->
                val total = total.getTotalById(f.objectId ?: "")
                AdapterItems.FolderItem(
                    f.name ?: "null",
                    "",
                    countInner = total.totalCount.getTotalString("count"),
                    total = total.totalPrice.getTotalString("total"),
                    objectId = f.objectId
                )
            })
            add(AdapterItems.ButtonAddItem(ButtonType.Folder))
        }
    }

//    private fun List<FileRow>.groupByPath(path: String): List<FileRow> {
//       return this.filter { it.path?.contains(path) == true }
//    }
//    fun makeFolderItems(path: String, map: Map<String?, List<FolderDTO>>?): List<AdapterItems> {
//        return map?.mapNotNull { entry ->
//            entry.key?.let { name ->
//                val currentPath = Path(path).addToPath(name)
//                val currentFolder = entry.value.find { it.path == currentPath }
//                AdapterItems.FolderItem(
//                    name,
//                    currentPath,
//                    countInner = "count:${entry.value.size}",
//                    total = "total: 0",
//                    objectId = currentFolder?.objectId
//                )
//            }
//        } ?: emptyList()
//    }

}
