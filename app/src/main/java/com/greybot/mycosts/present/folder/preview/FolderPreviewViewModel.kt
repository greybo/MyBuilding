package com.greybot.mycosts.present.folder.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.FolderDTO
import com.greybot.mycosts.data.dto.RowDto
import com.greybot.mycosts.data.repository.folder.FolderDataSource
import com.greybot.mycosts.data.repository.row.RowDataSource
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.file.addRowItems
import com.greybot.mycosts.utility.Event
import kotlinx.coroutines.async

class FolderPreviewViewModel : CompositeViewModel() {

    private val folderFindUseCase by lazy { FolderDataSource() }
    private val rowFindUseCase by lazy { RowDataSource() }

    private val _stateButton = MutableLiveData<Event<ButtonType>>()
    val stateButton: LiveData<Event<ButtonType>> = _stateButton

    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state

    private val rowListBackup = mutableListOf<RowDto>()

    fun fetchData(path: String?) {
        path ?: return
        launchOnIO {
            val folderSet = async { folderFindUseCase.findFolder(path) }
            val rowSet = async { rowFindUseCase.findByPath(path) }
            handleResult(folderSet.await(), rowSet.await())
        }
    }

    fun changeRowBuy(objectId: String) {
        makeRowList(rowListBackup.map {
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
        rowListBackup.clear()
        rowListBackup.addAll(rowList ?: emptyList())
        rowList ?: return

        val groups = rowList.groupBy { it.isBought }

        val itemList = mutableListOf<AdapterItems>()
        itemList.addRowItems(groups[false])
        itemList.addRowItems(groups[true])

        _state.postValue(itemList)
    }

    private fun makeFolderList(groups: Map<String?, List<FolderDTO>>?) {
        val folderItems = groups?.mapNotNull { entry ->
            entry.key?.let { folderCardMake(it, entry.value) }
        } ?: emptyList()

        _state.postValue(folderItems)
    }

//    private fun MutableList<AdapterItems>.buttonState(): ButtonType {
//        val type = when (this.getOrNull(0)) {
//            is AdapterItems.FolderItem -> ButtonType.Folder
//            is AdapterItems.RowItem -> ButtonType.Row
//            else -> ButtonType.None
//        }
//
//        _stateButton.postValue(Event(type))
//        return type
//    }

}

enum class ButtonType(val row: String) {
    Folder("Folder"), Row("Row"), None("")
}