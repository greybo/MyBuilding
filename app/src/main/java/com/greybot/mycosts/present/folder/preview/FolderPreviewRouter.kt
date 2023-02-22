package com.greybot.mycosts.present.folder.preview

import androidx.navigation.NavController

class FolderPreviewRouter(private val navController: NavController)  {

    fun fromFolderToFolder(id: String) {
        val direction = FolderPreviewFragmentDirections.toFolderPreviewFragment(id)
        navController.navigate(direction)
    }

    fun fromFolderToAddFolder(id: String) {
        val direction = FolderPreviewFragmentDirections.toFolderAddFragment(id)
        navController.navigate(direction)
    }

    fun fromFolderToAddRow(id: String) {
        val direction = FolderPreviewFragmentDirections.toRowAddFragment(id)
        navController.navigate(direction)
    }

    fun fromFolderToEditRow(id: String) {
        val direction = FolderPreviewFragmentDirections.toRowEditFragment(id)
        navController.navigate(direction)
    }
}