package com.greybot.mycosts.base

import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.present.main.IMainExploreRouter
import com.greybot.mycosts.utility.ROOT_FOLDER

class RouterCallbackHandler(private val router: IMainExploreRouter) {
    operator fun invoke(callback: AdapterCallback) {
        when (callback) {
            is AdapterCallback.FolderOpen -> {
//                binding.exploreFloatButton.animateFabHide {
                router.fromExploreToFolder(callback.value.objectId ?: ROOT_FOLDER)
//                }
            }
            is AdapterCallback.FolderHighlight -> {
//                binding.exploreFloatButton.animateFabHide {
//                    router.fromExploreToFolder(, callback.value.path)
//                }
            }
            is AdapterCallback.FolderAdd -> {
                router.fromExploreToAddFolder("root")
            }
            else -> TODO()
        }
    }
}
