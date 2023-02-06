package com.greybot.mycosts.present.second.preview

interface IFolderPreviewRouter {
    fun fromFolderToFolder(id: String)
    fun fromFolderToAddFolder(id: String)
    fun fromFolderToAddRow(id: String)
    fun fromFolderToEditRow(id: String)
}