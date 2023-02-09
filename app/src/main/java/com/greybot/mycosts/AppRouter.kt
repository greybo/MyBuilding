package com.greybot.mycosts

import androidx.navigation.NavController
import com.greybot.mycosts.present.folder.preview.FolderPreviewFragmentDirections
import com.greybot.mycosts.present.folder.preview.IFolderPreviewRouter
import com.greybot.mycosts.present.main.IMainExploreRouter
import com.greybot.mycosts.present.main.MainExploreFragmentDirections

class AppRouter(private val navController: NavController) : IMainExploreRouter,
    IFolderPreviewRouter {

    override fun fromFolderToFolder(id: String) {
        val direction = FolderPreviewFragmentDirections.toFolderPreviewFragment(id)
        navController.navigate(direction)
    }

    override fun fromFolderToAddFolder(id: String) {
        val direction = FolderPreviewFragmentDirections.toFolderAddFragment(id)
        navController.navigate(direction)
    }

    override fun fromFolderToAddRow(id: String) {
        val direction = FolderPreviewFragmentDirections.toRowAddFragment(id)
        navController.navigate(direction)
    }

    override fun fromFolderToEditRow(id: String) {
        val direction = FolderPreviewFragmentDirections.toRowEditFragment(id)
        navController.navigate(direction)
    }

    override fun fromExploreToFolder(objectId: String) {
        val direction = MainExploreFragmentDirections.toFolderPreviewFragment(objectId)
        navController.navigate(direction)
    }

    override fun fromExploreToAddFolder(id: String) {
        val direction = MainExploreFragmentDirections.toExploreAddRootFragment(id)
        navController.navigate(direction)
    }

    override fun fromExploreToEditFolder(objectId: String) {
        val direction = MainExploreFragmentDirections.toFolderEditFragment(objectId)
        navController.navigate(direction)
    }
}