package com.greybot.mycosts.present.folder.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.base.FindFolderUseCases
import com.greybot.mycosts.base.RowFolderUseCases
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.utility.Event
import kotlinx.coroutines.async

class FolderPreviewViewModel : CompositeViewModel() {

    //    private val folderAddUseCase get() = AddFolderUseCases()
    private val folderFindUseCase get() = FindFolderUseCases()
    private val rowFindUseCase get() = RowFolderUseCases()

    private val _stateButton = MutableLiveData<Event<ButtonType>>()
    val stateButton: LiveData<Event<ButtonType>> = _stateButton

    private var _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state

    private val rowListBackup = mutableListOf<AdapterItems.RowItem>()

    fun fetchData(path: String?) {
        path ?: return
        launchOnIO {
            val folderSet = async { folderFindUseCase.invoke(path) }
            val rowSet = async { rowFindUseCase.invoke(path) }
            handleResult(folderSet.await()?.toMutableList(), rowSet.await()?.toMutableList())
        }
    }

    fun changeRowBuy(item: AdapterItems.RowItem) {
        makeRowList(rowListBackup.map {
            if (it.objectId == item.objectId) {
                it.copy(check = !it.check)
            } else it
        })
    }

    private fun handleResult(
        folderList: MutableList<AdapterItems.FolderItem>?,
        rowList: MutableList<AdapterItems.RowItem>?
    ) {
        var type = ButtonType.None
        if (!folderList.isNullOrEmpty()) {
            type = ButtonType.Folder
            makeFolderList(folderList)
        } else if (!rowList.isNullOrEmpty()) {
            type = ButtonType.Row

            makeRowList(rowList)
        } else {
            makeButtonsList()
        }
        _stateButton.postValue(Event(type))
    }

    private fun makeButtonsList() {
        val itemList = listOf(
            AdapterItems.ButtonAddItem(ButtonType.Folder),
            AdapterItems.ButtonAddItem(ButtonType.Row)
        )
        _state.postValue(itemList)
    }

    private fun makeRowList(rowList: List<AdapterItems.RowItem>) {
        val itemList = mutableListOf<AdapterItems>()
        rowListBackup.clear()
        rowListBackup.addAll(rowList)

        val todoList = rowList.filter { !it.check }
        if (todoList.isNotEmpty()) {
            val todoTotal = getTotalPrice(todoList)

            itemList.addAll(todoList)
            itemList.add(AdapterItems.TotalItem(todoTotal))
        }

        val boughtList = rowList.filter { it.check }
        if (boughtList.isNotEmpty()) {
            val boughtTotal = getTotalPrice(boughtList)

            itemList.addAll(boughtList)
            itemList.add(AdapterItems.TotalItem(boughtTotal))
        }

        _state.postValue(itemList)
    }

    private fun makeFolderList(folderList: MutableList<AdapterItems.FolderItem>) {
        _state.postValue(folderList)
    }

    private fun getTotalPrice(itemList: List<AdapterItems.RowItem>) =
        itemList.fold(0F) { t, item ->
            t + item.price
        }

    private fun MutableList<AdapterItems>.buttonState(): ButtonType {
        val type = when (this.getOrNull(0)) {
            is AdapterItems.FolderItem -> ButtonType.Folder
            is AdapterItems.RowItem -> ButtonType.Row
            else -> ButtonType.None
        }

        _stateButton.postValue(Event(type))
        return type
    }

}

enum class ButtonType(val row: String) {
    Folder("Folder"), Row("Row"), None("")
}