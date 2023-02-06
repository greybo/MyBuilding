package com.greybot.mycosts.present.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.ExploreRow
import com.greybot.mycosts.data.repository.explore.ExploreDataSource
import com.greybot.mycosts.data.repository.explore.getOrNull
import com.greybot.mycosts.data.repository.row.FileDataSource
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.second.preview.ItemTotalHelper
import com.greybot.mycosts.utility.getTotalString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainExploreViewModel @Inject constructor(
    private val dataSource: ExploreDataSource,
    private var rowSource: FileDataSource
) : CompositeViewModel() {

    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state
    var force = true
    fun fetchData() {
        launchOnDefault {
            val rowGrouts = rowSource.fetch(force)
            val folders = dataSource.fetchData(force)
            force = false
            val totalHandler = ItemTotalHelper(folders, rowGrouts)
            makeFolderItems(folders.getOrNull("root"), totalHandler)
        }
    }

    private fun makeFolderItems(explores: List<ExploreRow>?, totalHandler: ItemTotalHelper) {
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


