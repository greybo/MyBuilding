package com.greybot.mycosts.present.folder.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.FolderDTO
import com.greybot.mycosts.data.dto.RowDto
import com.greybot.mycosts.data.repository.folder.FolderDataSource
import com.greybot.mycosts.data.repository.row.RowDataSource
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.file.RowHandler
import com.greybot.mycosts.utility.Event
import kotlinx.coroutines.async

class FolderPreviewViewModel : CompositeViewModel() {

    private val folderDataSource by lazy { FolderDataSource() }
    private val rowDataSource by lazy { RowDataSource() }
    private val rowHandler by lazy { RowHandler() }
    private val folderHandler by lazy { FolderHandler() }

    private val _stateButton = MutableLiveData<Event<ButtonType>>()
    val stateButton: LiveData<Event<ButtonType>> = _stateButton

    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state

    private var currentPath = ""

    fun fetchData(path: String?) {
        currentPath = path ?: return
        launchOnIO {
            val folderSet = async { folderDataSource.findFolder(path) }
            val rowSet = async { rowDataSource.findByPath(path) }
            handleResult(folderSet.await(), rowSet.await())
        }
    }

    fun changeRowBuy(objectId: String) {
        makeRowList(rowDataSource.list.map {
            if (it.objectId == objectId) {
                it.copy(isBought = !it.isBought)
            } else it
        })
    }

    private fun handleResult(
        folderList: Map<String?, List<FolderDTO>>?,
        rowList: List<RowDto>?
    ) {
        var type = ButtonType.None
        if (!folderList.isNullOrEmpty()) {
            type = ButtonType.Folder
            makeFolderList(folderList)
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

    private fun makeFolderList(list: Map<String?, List<FolderDTO>>?) {
        val folderItems = folderHandler.makeFolderItems(currentPath, list)
        _state.postValue(folderItems)
    }
}

enum class ButtonType(val row: String) {
    Folder("Folder"), Row("Row"), None("")
}