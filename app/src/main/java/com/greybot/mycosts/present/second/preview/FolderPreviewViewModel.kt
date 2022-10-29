package com.greybot.mycosts.present.second.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.AppCoordinator
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.FolderDTO
import com.greybot.mycosts.data.dto.RowDto
import com.greybot.mycosts.data.repository.ExploreRepository
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.row.RowHandler
import com.greybot.mycosts.present.second.FolderHandler
import com.greybot.mycosts.utility.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class FolderPreviewViewModel @Inject constructor(private val exploreRepo: ExploreRepository) :
    CompositeViewModel() {

    private val folderDataSource get() = AppCoordinator.shared.folderDataSource
    private val rowDataSource get() = AppCoordinator.shared.rowDataSource
    private val rowHandler by lazy { RowHandler() }
    private val folderHandler by lazy { FolderHandler() }

    private val _stateButton = MutableLiveData<Event<ButtonType>>()
    val stateButton: LiveData<Event<ButtonType>> = _stateButton

    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state

    var objectId: String? = null
    private var currentPath = ""

    fun fetchData(path: String?) {
        currentPath = path ?: return
        launchOnIO {
            val folderSet = async { folderDataSource.findFolder(path) }
            val rowSet = async { rowDataSource.findByPath(path) }
            handleResult(folderSet.await(), rowSet.await())
        }
    }

    fun changeRowBuy(item: AdapterItems.RowItem) {
        rowDataSource.changeBuyStatus(item.objectId)
        makeRowList(rowDataSource.geBackupList())
    }

    private fun handleResult(
        folderList: Map<String?, List<FolderDTO>>?,
        rowList: List<RowDto>?
    ) {
        var type = ButtonType.None
        if (makeFolderList(folderList)) {
            type = ButtonType.Folder
        } else if (!rowList.isNullOrEmpty()) {
            type = ButtonType.Row
            makeRowList(rowList)
        } else {
            makeButtonList()
        }
        _stateButton.postValue(Event(type))
    }

    private fun makeButtonList() {
        val itemList = listOf(
            AdapterItems.ButtonAddItem(ButtonType.Folder),
            AdapterItems.ButtonAddItem(ButtonType.Row)
        )
        _state.postValue(itemList)
    }

    private fun makeRowList(rowList: List<RowDto>?) {
        rowList ?: return
        val itemList = rowHandler.makeGroupBuy(rowList)
        _state.postValue(itemList)
    }

    private fun makeFolderList(map: Map<String?, List<FolderDTO>>?): Boolean {
        objectId = map?.getOrDefault(null, null)?.getOrNull(0)?.objectId
        val folderItems = folderHandler.makeFolderItems(currentPath, map)
        return if (folderItems.isNotEmpty()) {
            _state.postValue(folderItems)
            true
        } else false
    }
}

enum class ButtonType(val row: String) {
    Folder("Folder"), Row("Row"), None("")
}