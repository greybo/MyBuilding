package com.greybot.mybuilding.ui.explore

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.greybot.mybuilding.R

abstract class ExploreRouter() {
    abstract val navController: NavController

    fun toFolder(folder: String) {
        navController.navigate(R.id.toFolderPreviewFragment, bundleOf("folderName" to folder))
    }
}