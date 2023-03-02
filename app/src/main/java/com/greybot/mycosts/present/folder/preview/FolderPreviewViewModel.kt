package com.greybot.mycosts.present.folder.preview

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.components.toolbar.ActionButtonType
import com.greybot.mycosts.components.toolbar.ActionToolbar
import com.greybot.mycosts.components.toolbar.ToolbarModel
import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.data.dto.FolderRow
import com.greybot.mycosts.data.repository.file.FileDataSource
import com.greybot.mycosts.data.repository.folder.FolderDataSource
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.adapter.AdapterCallback
import com.greybot.mycosts.present.file.FileHandler
import com.greybot.mycosts.present.folder.FolderHandler
import com.greybot.mycosts.utility.LogApp
import com.greybot.mycosts.utility.ResultEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderPreviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val exploreSource: FolderDataSource,
    private val rowSource: FileDataSource
) : CompositeViewModel() {

    private val toolbarIconMenu
        get() = _toolbarModelLiveData.value?.copy(
            rightAction = ActionToolbar(ActionButtonType.Menu)
        )
    private val toolbarIconDelete
        get() = _toolbarModelLiveData.value?.copy(
            rightAction = ActionToolbar(ActionButtonType.Delete, Color.Red)
        )
    private val fileHandler by lazy { FileHandler() }
    private val listDelete =  mutableListOf<String>() //DeleteListActor()//
    private val _dialogCostsLiveData = MutableLiveData<ResultEvent<AdapterCallback>>()
    val dialogCostsLiveData: LiveData<ResultEvent<AdapterCallback>> = _dialogCostsLiveData
    private val _toolbarModelLiveData =
        MutableLiveData(ToolbarModel(callback = ::handleOnClickOptionMenu))
    val toolbarModelLiveData: LiveData<ToolbarModel> = _toolbarModelLiveData
    private val _state = MutableLiveData<List<AdapterItems>>()
    private val parentId by lazy { savedStateHandle.get<String>("objectId") ?: "" }

    val state: LiveData<List<AdapterItems>> = _state
    var router: FolderPreviewRouter? = null

    init {
        launchOnDefault {
            val parentFolder = exploreSource.findByObjectId(parentId)
            _toolbarModelLiveData.postValue(_toolbarModelLiveData.value?.copy(title = parentFolder?.name))
        }
    }

    fun fetchData() {
        launchOnDefault {
            listDelete.clear()
            fetchDataSuspend()
        }
    }

    private suspend fun fetchDataSuspend(id: String = parentId) {
        val folderList = exploreSource.getListById(id)
        val files = rowSource.getListById(id)

        if (!folderList.isNullOrEmpty()) {
            makeFolderList(folderList)
        } else if (files.isNotEmpty()) {
            makeFileList(files)
        } else makeButtonList()

        _toolbarModelLiveData.postValue(
            if (listDelete.isEmpty()) {
                toolbarIconMenu
            } else toolbarIconDelete
        )
    }

    private suspend fun makeFolderList(list: List<FolderRow>) {
        val folderHandler = FolderHandler(exploreSource.fetchData(), rowSource.fetchData())
        setToLiveData(folderHandler.makeFolderItems(list))
    }

    private fun makeFileList(rowList: List<FileRow>) {
        setToLiveData(fileHandler.makeGroupBuy(rowList, listDelete))
    }

    private fun changeRowBuy(item: AdapterItems.RowItem) {
        launchOnDefault {
            rowSource.changeBuyStatus(item.objectId)
            fetchDataSuspend()
        }
    }

    private fun changeRowPrice(id: String, count: Double, price: Double) {
        launchOnDefault {
            rowSource.changePrice(id, count, price)
            fetchDataSuspend()
        }
    }

    private fun makeButtonList() {
        setToLiveData(
            listOf(
                AdapterItems.ButtonAddItem(ButtonType.Folder),
                AdapterItems.ButtonAddItem(ButtonType.Row)
            )
        )
    }

    private fun fileHighlight(objectId: String) {
        launchOnDefault {
            if (listDelete.contains(objectId)) {
                listDelete.remove(objectId)
            } else {
                listDelete.add(objectId)
            }
            fetchDataSuspend()
        }
    }

    private fun deleteSelectItems() {
        launchOnDefault {
            listDelete.map { objectId ->
                rowSource.delete(objectId)
            }
            listDelete.clear()
            fetchDataSuspend()
        }
    }

    private fun setToLiveData(list: List<AdapterItems>): List<AdapterItems> {
        _state.postValue(list)
        return _state.value ?: emptyList()
    }


    fun saveData(model: AdapterItems.RowItem) {
        LogApp.d("log_tag", "${model.count} | ${model.price}")
        changeRowPrice(id = model.objectId, count = model.count, price = model.price)
    }

    fun handleAdapterClick(callback: AdapterCallback) {
        with(callback) {
            when (this) {
                is AdapterCallback.RowName -> router?.fromFolderToEditRow(value.objectId)
                is AdapterCallback.RowPrice,
                is AdapterCallback.RowCount -> _dialogCostsLiveData.value =
                    ResultEvent.initial(this) //bottomDialog(this)
                is AdapterCallback.FileHighlight -> fileHighlight(value.objectId)
                is AdapterCallback.RowBuy -> changeRowBuy(value)
                is AdapterCallback.AddButton -> handleButtonClick(value.type)
                is AdapterCallback.FolderOpen -> {
                    router?.fromFolderToFolder(
                        value.objectId ?: throw Throwable("objectId must not be empty")
                    )
                }
                else -> {}
            }
        }
    }

    fun handleOnClickOptionMenu(type: ActionButtonType) {
        when (type) {
            ActionButtonType.Back -> router?.popBackStack()
            ActionButtonType.Menu -> {}
            ActionButtonType.Delete -> deleteSelectItems()
        }
    }

    private fun handleButtonClick(
        type: ButtonType,
        id: String = parentId
    ) {
        when (type) {
            ButtonType.Folder -> router?.fromFolderToAddFolder(id)
            ButtonType.Row -> router?.fromFolderToAddRow(id)
            else -> {}
        }
    }
}

enum class ButtonType(val row: String) {
    Folder("Папка"), Row("Товар"), None("")
}

