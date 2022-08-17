package com.greybot.mybuilding

import androidx.navigation.NavController
import com.greybot.mybuilding.present.explore.ExploreFragmentDirections
import com.greybot.mybuilding.present.explore.IExploreRouter
import com.greybot.mybuilding.present.folder.preview.FolderPreviewFragmentDirections

class AppRouter(private val navController: NavController) : IExploreRouter {

    override fun fromFolderToFolder(pathName: String) {
        val direction = FolderPreviewFragmentDirections.toFolderPreviewFragment(pathName)
        navController.navigate(direction)
    }

    override fun fromExploreToFolder(pathName: String) {
        val direction = ExploreFragmentDirections.toFolderPreviewFragment(pathName)
        navController.navigate(direction)
    }
}