package com.greybot.mycosts.present.folder.preview

interface IFolderPreviewRouter {
    fun fromFolderToFolder(pathName: String)
    fun fromFolderToAddFolder(path: String)
    fun fromFolderToAddRow(path: String)
    fun fromFolderToEditRow(id: String)
}