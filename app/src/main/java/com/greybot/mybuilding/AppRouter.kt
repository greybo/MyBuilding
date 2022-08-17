package com.greybot.mybuilding

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.greybot.mybuilding.present.explore.IExploreRouter

class AppRouter(private val navController: NavController) : IExploreRouter {

    override fun toFolder(pathName: String) {
        navController.navigate(R.id.toFolderPreviewFragment, bundleOf("folderName" to pathName))
    }
}