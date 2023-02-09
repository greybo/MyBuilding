package com.greybot.mycosts.present.folder

import com.greybot.mycosts.data.dto.ExploreRow
import com.greybot.mycosts.models.AdapterItems

class ExploreHandler {

    fun makeFolderItems(explores: List<ExploreRow>?): List<AdapterItems> {
        return explores?.map { f ->
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
