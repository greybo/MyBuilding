package com.greybot.mycosts.present.second.add

import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.Folder
import com.greybot.mycosts.data.repository.ExploreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderEditViewModel @Inject constructor(private val exploreRepo: ExploreRepository) :
    CompositeViewModel() {

    fun getFolderById(objectId: String): Folder {
        return exploreRepo.findFolder(objectId)
    }

    fun updateFolderNew(name: String?) {
        if (name != null) {
            val explore = Folder(name)
            exploreRepo.update(explore)
        }
    }
}