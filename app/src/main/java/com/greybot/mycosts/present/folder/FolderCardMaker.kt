package com.greybot.mycosts.present.folder.preview

import com.greybot.mycosts.data.dto.FolderDTO
import com.greybot.mycosts.models.AdapterItems

fun folderCardMake(name: String, path: String, list: List<FolderDTO>): AdapterItems {
    return AdapterItems.FolderItem(name, formatPathFolder(path, name), list.size)
}

fun formatPathFolder(path: String, name: String): String {
    return path.split("/").toMutableList().also { list ->
        list.add(name)
    }.filter { it.isNotBlank() }.joinToString("/", "/")
}
