package com.greybot.mycosts.present.folder.add

import androidx.lifecycle.SavedStateHandle
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.ExploreRow
import com.greybot.mycosts.data.repository.explore.FolderDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderAddViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val source: FolderDataSource
) : CompositeViewModel() {

    val objectId by lazy { savedStateHandle.get<String>("objectId") }

    fun addFolderNew(name: String?, timestamp: Long) {
        if (name != null) {
            val exploreNew = ExploreRow(
                name = name,
                parentObjectId = objectId ?: throw Throwable(" objectId must not be null"),
                timestamp = timestamp
            )

            launchOnDefault {
                source.addFolder(exploreNew)
            }
        }
    }
}