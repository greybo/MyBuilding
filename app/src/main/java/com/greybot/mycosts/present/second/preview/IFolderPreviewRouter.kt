package com.greybot.mycosts.present.second.preview

interface IFolderPreviewRouter {
    fun fromFolderToFolder(pathName: String)
    fun fromFolderToAddFolder(path: String)
    fun fromFolderToAddRow(path: String, id: String)
    fun fromFolderToEditRow(id: String)
}