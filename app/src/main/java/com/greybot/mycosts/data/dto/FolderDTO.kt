package com.greybot.mycosts.data.dto

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class FolderDTO(
    var objectId: String? = null,
    val path: String = "",
    var name: String = "",
    var date: Long = Date().time,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FolderDTO

        if (objectId != other.objectId) return false
        if (path != other.path) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = objectId?.hashCode() ?: 0
        result = 31 * result + path.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "objectId" to objectId,
            "path" to path,
            "name" to name,
        )
    }
}