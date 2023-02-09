package com.greybot.mycosts.present.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.FolderRow
import com.greybot.mycosts.data.repository.file.FileDataSource
import com.greybot.mycosts.data.repository.folder.FolderDataSource
import com.greybot.mycosts.data.repository.folder.getOrNull
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.folder.preview.ItemTotalHelper
import com.greybot.mycosts.utility.getTotalString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainExploreViewModel @Inject constructor(
    private val dataSource: FolderDataSource,
    private var rowSource: FileDataSource
) : CompositeViewModel() {

    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state
    var _force:Boolean = true
    fun fetchData(force:Boolean = _force) {
        launchOnDefault {
            val rowGrouts = rowSource.fetchData(force)
            val folders = dataSource.fetchData(force)
            _force = false
            val totalHandler = ItemTotalHelper(folders, rowGrouts)
            makeFolderItems(folders.getOrNull("root"), totalHandler)
        }
    }

    private fun makeFolderItems(explores: List<FolderRow>?, totalHandler: ItemTotalHelper) {
        val items = explores?.map { f ->

            val total = totalHandler.getTotalById(f.objectId ?: "")
            AdapterItems.FolderItem(
                f.name ?: "null",
                "",
                countInner = total.totalCount.getTotalString("count"),
                total = total.totalPrice.getTotalString("total"),
                objectId = f.objectId
            )
        } ?: emptyList()
        _state.postValue(items)
    }

}


