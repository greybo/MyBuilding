package com.greybot.mycosts.data.dto

import com.google.gson.annotations.SerializedName
import java.util.*

//data class Explore(
//    val name: String? = null,
//    var date: Long? = null,
//    val files: List<FileRow> = emptyList(),
//    var objectId: String? = null,
//) {
//
//    fun toMap(): Map<String, Any?> {
//        return mapOf(
//            "objectId" to objectId,
//            "name" to name,
//            "files" to files
//        )
//    }
//}

data class ExploreRow(
    @SerializedName("Name")
    val name: String? = null,
    @SerializedName("ObjectId")
    var objectId: String? = null,
    @SerializedName("ParentObjectId")
    var parentObjectId: String? = null,
    @SerializedName("Files_is")
    val isFiles: Boolean = false,
    @SerializedName("Delete_is")
    val isDelete: Boolean = false,
    @SerializedName("Archive_is")
    val isArchive: Boolean = false,
    @SerializedName("Date")
    var date: Long = Date().time,
) {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "Name" to name,
            "Date" to date,
            "ObjectId" to objectId,
            "ParentObjectId" to parentObjectId,
            "Files_is" to isFiles,
            "Delete_is" to isDelete,
            "Archive_is" to isArchive,
        )
    }
}