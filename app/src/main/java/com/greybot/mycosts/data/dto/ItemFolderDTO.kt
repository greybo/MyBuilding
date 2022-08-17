package com.greybot.mycosts.data.dto

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ItemFolderDTO(
    var objectId: String? = null,
    val path: String = "",
    var name: String = "",
    val files: List<FileDto>? = emptyList()
)