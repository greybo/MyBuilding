package com.greybot.mycosts.present.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.AppCoordinator
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.repository.ExploreRepository
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.second.FolderHandler
import javax.inject.Inject


//TODO init with hilt
class MainExploreViewModel @Inject constructor(val exploreRepo: ExploreRepository) :
    CompositeViewModel() {

    private val folderDataSource get() = AppCoordinator.shared.folderDataSource
    private val folderHandler by lazy { FolderHandler() }

    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state

    fun fetchData(path: String = "") {
        findItems(path)
    }

    private fun findItems(path: String) {
        launchOnDefault {
            val folders = folderDataSource.findFolder(path)
            val items = folderHandler.makeFolderItems(path, folders)
            _state.postValue(items)
        }
    }
}
