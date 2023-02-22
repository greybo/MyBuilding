package com.greybot.mycosts.present.main

import androidx.navigation.NavController
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.utility.ROOT_FOLDER

class MainExploreRouter(private val navController: NavController) {

    operator fun invoke(action: AdapterCallback) {
        when (action) {
            is AdapterCallback.FolderOpen -> {
                fromExploreToFolder(action.value.objectId ?: ROOT_FOLDER)
            }
            is AdapterCallback.FolderHighlight -> {
//                    router.fromExploreToFolder(, callback.value.path)
            }
            is AdapterCallback.FolderAdd -> {
                fromExploreToAddFolder(action.id)
            }
            else -> TODO()
        }
    }

    fun fromExploreToFolder(objectId: String) {
        val direction = MainExploreFragmentDirections.toFolderPreviewFragment(objectId)
        navController.navigate(direction)
    }

    fun fromExploreToAddFolder(id: String) {
        val direction = MainExploreFragmentDirections.toExploreAddRootFragment(id)
        navController.navigate(direction)
    }

    fun fromExploreToEditFolder(objectId: String) {
        val direction = MainExploreFragmentDirections.toFolderEditFragment(objectId)
        navController.navigate(direction)
    }
}