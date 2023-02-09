package com.greybot.mycosts.present.folder.preview

interface IFolderPreviewRouter {
    fun fromFolderToFolder(id: String)
    fun fromFolderToAddFolder(id: String)
    fun fromFolderToAddRow(id: String)
    fun fromFolderToEditRow(id: String)
}