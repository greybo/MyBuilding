package com.greybot.mycosts.present.folder.preview

import com.greybot.mycosts.base.Path
import com.greybot.mycosts.data.dto.FolderDTO
import com.greybot.mycosts.models.AdapterItems

class FolderHandler {

    fun makeFolderItems(path: String, map: Map<String?, List<FolderDTO>>?): List<AdapterItems> {
        return map?.mapNotNull { entry ->
            entry.key?.let { name ->
                AdapterItems.FolderItem(name, Path(path).addToPath(name), entry.value.size)
            }
        } ?: emptyList()
    }
}
