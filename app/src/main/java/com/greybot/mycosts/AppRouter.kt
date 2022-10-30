package com.greybot.mycosts

import androidx.navigation.NavController
import com.greybot.mycosts.present.main.IMainExploreRouter
import com.greybot.mycosts.present.main.MainExploreFragmentDirections
import com.greybot.mycosts.present.second.preview.FolderPreviewFragmentDirections
import com.greybot.mycosts.present.second.preview.IFolderPreviewRouter

class AppRouter(private val navController: NavController) : IMainExploreRouter,
    IFolderPreviewRouter {

    override fun fromFolderToFolder(id: String, pathName: String) {
        val direction = FolderPreviewFragmentDirections.toFolderPreviewFragment(pathName)
        navController.navigate(direction)
    }

    override fun fromFolderToAddFolder(id: String, path: String) {
        val direction = FolderPreviewFragmentDirections.toFolderAddFragment(id, path)
        navController.navigate(direction)
    }

    override fun fromFolderToAddRow(id: String, path: String) {
        val direction = FolderPreviewFragmentDirections.toRowAddFragment(path, id)
        navController.navigate(direction)
    }

    override fun fromFolderToEditRow(id: String, path: String) {
        val direction = FolderPreviewFragmentDirections.toRowEditFragment(id, path)
        navController.navigate(direction)
    }

    override fun fromExploreToFolder(objectId: String, pathName: String) {
        val direction = MainExploreFragmentDirections.toFolderPreviewFragment(objectId, pathName)
        navController.navigate(direction)
    }

    override fun fromExploreToAddFolder() {
        val direction = MainExploreFragmentDirections.toExploreAddRootFragment()
        navController.navigate(direction)
    }

    override fun fromExploreToEditFolder(objectId: String) {
        val direction = MainExploreFragmentDirections.toFolderEditFragment(objectId)
        navController.navigate(direction)
    }
}