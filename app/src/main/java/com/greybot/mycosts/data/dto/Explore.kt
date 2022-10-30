package com.greybot.mycosts.data.dto

import com.google.firebase.database.PropertyName
import java.util.*

data class Explore(
    val name: String? = null,
    var date: Long? = null,
    val fileRows: List<FileRow> = emptyList(),
    var objectId: String? = null,
) {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "objectId" to objectId,
            "name" to name,
            "files" to fileRows
        )
    }
}

data class FileRow(
    @PropertyName("Name")
    private val _name: String? = null,
    override val path: String? = null,
    override val measure: String? = null,
    override val count: Int = 1,
    override val price: Float = 0F,
    override val bought: Boolean = false,
    override val currency: CurrencyDto? = CurrencyDto(),
    override val data: Long = Date().time,
    override val isFile: Boolean = false,
    override val isDelete: Boolean = false,
    override val isArchive: Boolean = false,
) : IFile, IFolder {
    override val name: String = _name ?: ""
}

interface IFile {
    val name: String
    val path: String?
    val measure: String?
    val count: Int
    val price: Float
    val bought: Boolean
    val currency: CurrencyDto?
    val data: Long
    val isFile: Boolean
    val isDelete: Boolean
    val isArchive: Boolean
}

interface IFolder {
    val isDelete: Boolean
    val isArchive: Boolean
}