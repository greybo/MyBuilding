package com.greybot.mycosts.data.dto

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class ExploreRow(
    val name: String? = null,
    var objectId: String? = null,
    var parentObjectId: String? = null,
    val files_is: Boolean = false,
    val delete_is: Boolean = false,
    val archive_is: Boolean = false,
    var date: Long = Date().time,
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "date" to date,
            "objectId" to objectId,
            "parentObjectId" to parentObjectId,
            "files_is" to files_is,
            "delete_is" to delete_is,
            "archive_is" to archive_is,
        )
    }
}