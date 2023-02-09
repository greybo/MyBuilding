package com.greybot.mycosts.present.folder.add

import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.ExploreRow
import com.greybot.mycosts.data.repository.explore.ExploreDataSource
import com.greybot.mycosts.utility.makeLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderEditViewModel @Inject constructor(private val source: ExploreDataSource) :
    CompositeViewModel() {

    val state = makeLiveData<ExploreRow?>()

    fun fetchData(objectId: String) {
        launchOnDefault {
            val model = source.findByObjectId(objectId)
            state.values = model
        }
    }

    fun updateFolderNew(name: String?) {
        launchOnDefault {
            if (name != null) {
                val explore = ExploreRow(name)
                source.updateFolder(explore)
            }
        }
    }
}