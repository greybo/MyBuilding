package com.greybot.mycosts.present.folder.preview

import com.greybot.mycosts.data.dto.FolderDTO
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.utility.formatPathFolder

fun folderCardMake(name: String, list: List<FolderDTO>): AdapterItems {
    return AdapterItems.FolderItem(name, name.formatPathFolder(), list.size)
}
