package com.greybot.mycosts

import androidx.navigation.NavController
import com.greybot.mycosts.present.explore.ExploreFragmentDirections
import com.greybot.mycosts.present.explore.IExploreRouter
import com.greybot.mycosts.present.folder.preview.FolderPreviewFragmentDirections
import com.greybot.mycosts.present.folder.preview.IFolderPreviewRouter

class AppRouter(private val navController: NavController) : IExploreRouter, IFolderPreviewRouter {

    override fun fromFolderToFolder(pathName: String) {
        val direction = FolderPreviewFragmentDirections.toFolderPreviewFragment(pathName)
        navController.navigate(direction)
    }

    override fun fromExploreToFolder(pathName: String) {
        val direction = ExploreFragmentDirections.toFolderPreviewFragment(pathName)
        navController.navigate(direction)
    }

    override fun fromExploreToAddFolder(nameFolder: String?) {
        val direction = ExploreFragmentDirections.toFolderAddFragment(nameFolder)
        navController.navigate(direction)
    }
}