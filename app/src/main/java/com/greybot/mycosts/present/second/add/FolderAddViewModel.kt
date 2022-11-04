package com.greybot.mycosts.present.second.add

import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.ExploreRow
import com.greybot.mycosts.data.repository.explore.ExploreDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderAddViewModel @Inject constructor(private val source: ExploreDataSource) :
    CompositeViewModel() {
    var explore: ExploreRow? = null

    fun fetchData(objectId: String?) {
        objectId ?: return
        launchOnDefault {
            val folder = source.findParent(objectId)
            withMain { explore = folder }
        }
    }

    fun addFolderNew(name: String?) {
        if (name != null) {
            val exploreNew = ExploreRow(
                name = name,
                parentObjectId = explore?.objectId
            )

            exploreNew.let {
                source.addFolder(it)
            }
        }
    }
}