package com.greybot.mycosts.present.second.add

import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.Explore
import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.data.repository.ExploreRepository
import com.greybot.mycosts.utility.myLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderEditViewModel @Inject constructor(private val exploreRepo: ExploreRepository) :
    CompositeViewModel() {

    val state = myLiveData<FileRow>()

    fun fetchData(objectId: String, path: String) {
        launchOnDefault {
            val model = exploreRepo.findById(objectId)?.files?.find { it.path == path }
            state.values = model ?: FileRow()
        }
    }

    fun updateFolderNew(name: String?) {
        if (name != null) {
            val explore = Explore(name)
            exploreRepo.update(explore)
        }
    }
}