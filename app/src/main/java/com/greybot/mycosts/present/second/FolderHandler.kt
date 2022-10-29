package com.greybot.mycosts.present.second

import com.greybot.mycosts.base.Path
import com.greybot.mycosts.data.dto.Folder
import com.greybot.mycosts.data.dto.FolderDTO
import com.greybot.mycosts.models.AdapterItems

class FolderHandler {

    fun makeFolderItems(path: String, map: Map<String?, List<FolderDTO>>?): List<AdapterItems> {
        return map?.mapNotNull { entry ->
            entry.key?.let { name ->
                val currentPath = Path(path).addToPath(name)
                val currentFolder = entry.value.find { it.path == currentPath }
                AdapterItems.FolderItem(
                    name,
                    currentPath,
                    countInner = "count:${entry.value.size}",
                    total = "total: 0",
                    objectId = currentFolder?.objectId
                )
            }
        } ?: emptyList()
    }

    fun makeFolderItems(folders: List<Folder>?): List<AdapterItems> {
        return folders?.map { f ->
//                val currentPath = Path(path).addToPath(name)
//                val currentFolder = entry.value.find { it.path == currentPath }
            AdapterItems.FolderItem(
                f.name ?: "2222",
                "",
                countInner = "count:${111}",
                total = "total: 0",
                objectId = f.objectId
            )
        } ?: emptyList()
    }
}
