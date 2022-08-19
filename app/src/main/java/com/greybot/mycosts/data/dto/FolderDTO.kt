package com.greybot.mycosts.data.dto

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class FolderDTO(
    var objectId: String? = null,
    val path: String = "",
    var name: String = "",
)