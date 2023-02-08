package com.greybot.mycosts.data.dto

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ExploreRow(
    val name: String? = null,
    var objectId: String? = null,
    var parentObjectId: String? = null,
    val files: Boolean = false,
    val delete: Boolean = false,
    val archive: Boolean = false,
    var timestamp: Long = 0L,
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "timestamp" to timestamp,
            "objectId" to objectId,
            "parentObjectId" to parentObjectId,
            "files" to files,
            "delete" to delete,
            "archive" to archive,
        )
    }
}