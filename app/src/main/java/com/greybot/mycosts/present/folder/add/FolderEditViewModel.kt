package com.greybot.mycosts.present.folder.add

import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.FolderRow
import com.greybot.mycosts.data.repository.folder.FolderDataSource
import com.greybot.mycosts.utility.makeLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderEditViewModel @Inject constructor(private val source: FolderDataSource) :
    CompositeViewModel() {

    val state = makeLiveData<FolderRow?>()

    fun fetchData(objectId: String) {
        launchOnDefault {
            val model = source.findByObjectId(objectId)
            state.values = model
        }
    }

    fun updateFolderNew(name: String?) {
        launchOnDefault {
            if (name != null) {
                val explore = FolderRow(name)
                source.updateFolder(explore)
            }
        }
    }
}