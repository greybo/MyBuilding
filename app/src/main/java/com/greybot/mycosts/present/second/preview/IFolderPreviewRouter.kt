package com.greybot.mycosts.present.second.preview

interface IFolderPreviewRouter {
    fun fromFolderToFolder(id: String, pathName: String)
    fun fromFolderToAddFolder(id: String, path: String)
    fun fromFolderToAddRow(id: String, path: String)
    fun fromFolderToEditRow(id: String, path: String)
}