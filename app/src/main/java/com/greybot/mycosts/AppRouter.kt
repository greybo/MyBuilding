package com.greybot.mycosts

import androidx.navigation.NavController
import com.greybot.mycosts.present.main.IMainExploreRouter
import com.greybot.mycosts.present.main.MainExploreFragmentDirections
import com.greybot.mycosts.present.second.preview.FolderPreviewFragmentDirections
import com.greybot.mycosts.present.second.preview.IFolderPreviewRouter

class AppRouter(private val navController: NavController) : IMainExploreRouter, IFolderPreviewRouter {

    override fun fromFolderToFolder(pathName: String) {
        val direction = FolderPreviewFragmentDirections.toFolderPreviewFragment(pathName)
        navController.navigate(direction)
    }

    override fun fromFolderToAddFolder(path: String) {
        val direction = FolderPreviewFragmentDirections.toFolderAddFragment(path)
        navController.navigate(direction)
    }

    override fun fromFolderToAddRow(path: String, id: String) {
        val direction = FolderPreviewFragmentDirections.toRowAddFragment(path,id)
        navController.navigate(direction)
    }

    override fun fromFolderToEditRow(id: String) {
        val direction = FolderPreviewFragmentDirections.toRowEditFragment(id)
        navController.navigate(direction)
    }

    override fun fromExploreToFolder(pathName: String) {
        val direction = MainExploreFragmentDirections.toFolderPreviewFragment(pathName)
        navController.navigate(direction)
    }

    override fun fromExploreToAddFolder(path: String?) {
        val direction = MainExploreFragmentDirections.toFolderAddFragment(path)
        navController.navigate(direction)
    }

    override fun fromExploreToEditFolder(objectId: String) {
        val direction = MainExploreFragmentDirections.toFolderEditFragment(objectId)
        navController.navigate(direction)
    }
}