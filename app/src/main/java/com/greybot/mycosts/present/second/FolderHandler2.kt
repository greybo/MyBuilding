package com.greybot.mycosts.present.second

import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.second.preview.ButtonType

class FolderHandler2 {

    fun makeFolderItems(list: List<FileRow>): List<AdapterItems> {
        return buildList {
            addAll(list.map { f ->
//                val currentPath = Path(path).addToPath(name)
//                val currentFolder = entry.value.find { it.path == currentPath }
                AdapterItems.FolderItem(
                    f.name ?: "2222",
                    "",
                    countInner = "count:${111}",
                    total = "total: 0",
                )
            })
            add(AdapterItems.ButtonAddItem(ButtonType.Folder))
        }
    }

    private fun List<FileRow>.groupByPath(path: String): List<FileRow> {
       return this.filter { it.path?.contains(path) == true }
    }
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
