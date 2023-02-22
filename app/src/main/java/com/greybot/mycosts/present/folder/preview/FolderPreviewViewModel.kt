package com.greybot.mycosts.present.folder.preview

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.data.dto.FolderRow
import com.greybot.mycosts.data.repository.file.FileDataSource
import com.greybot.mycosts.data.repository.folder.FolderDataSource
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.file.FileHandler
import com.greybot.mycosts.present.folder.FolderHandler
import com.greybot.mycosts.utility.LogApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderPreviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val exploreSource: FolderDataSource,
    private val rowSource: FileDataSource
) : CompositeViewModel() {

    private val fileHandler by lazy { FileHandler() }
    private val listDelete = mutableListOf<String>()
    private val actionIconLiveData = MutableLiveData(ActionButtonType.Menu)

    private val _state = MutableLiveData<List<AdapterItems>>()
    val state: LiveData<List<AdapterItems>> = _state
    val title = MutableLiveData<String?>()
    val parentId by lazy { savedStateHandle.get<String>("objectId") ?: "" }
    var router: FolderPreviewRouter? = null

    init {
        launchOnDefault {
            val parentFolder = exploreSource.findByObjectId(parentId)
            title.postValue(parentFolder?.name)
        }
    }

    fun fetchData(id: String = parentId) {
        launchOnDefault {
            val folderList = exploreSource.getListById(id)
            val files = rowSource.getListById(id)

            if (!folderList.isNullOrEmpty()) {
                makeFolderList(folderList)
            } else if (files.isNotEmpty()) {
                makeFileList(files)
            } else makeButtonList()
        }
    }

    private suspend fun makeFolderList(list: List<FolderRow>) {
        val folderHandler = FolderHandler(exploreSource.fetchData(), rowSource.fetchData())
        setToLiveData = folderHandler.makeFolderItems(list)
    }

    private fun makeFileList(rowList: List<FileRow>) {
        setToLiveData = fileHandler.makeGroupBuy(rowList)
    }

    fun changeRowBuy(item: AdapterItems.RowItem) {
        launchOnDefault {
            rowSource.changeBuyStatus(item.objectId)
            updateUIRowList()
        }
    }

    private suspend fun updateUIRowList() {
//        val files = rowSource.getCurrentList(parentId)
//        makeFileList(files)
        fetchData()
    }

    fun changeRowPrice(id: String, count: Double, price: Double) {
        launchOnDefault {
            rowSource.changePrice(id, count, price)
            updateUIRowList()
        }
    }

    private fun makeButtonList() {
        setToLiveData = listOf(
            AdapterItems.ButtonAddItem(ButtonType.Folder),
            AdapterItems.ButtonAddItem(ButtonType.Row)
        )
    }

    fun actionIconLiveData(): LiveData<ActionButtonType> {
        actionIconLiveData.value = ActionButtonType.Menu
        listDelete.clear()
        return actionIconLiveData
    }

    fun fileHighlight(objectId: String) {
        if (listDelete.contains(objectId)) {
            listDelete.remove(objectId)
        } else {
            listDelete.add(objectId)
        }
        actionIconLiveData.value = if (listDelete.isNotEmpty()) {
            ActionButtonType.Menu
        } else ActionButtonType.Delete
    }

    private fun deleteSelectItems() {
        launchOnDefault {
            listDelete.map { objectId ->
                rowSource.delete(objectId)
            }
            listDelete.clear()
            fetchData()
        }
        actionIconLiveData.value = ActionButtonType.Menu
    }

    private var setToLiveData: List<AdapterItems>
        get() = _state.value ?: emptyList()
        set(value) {
            _state.postValue(value)
        }


    fun saveData(model: AdapterItems.RowItem) {
        LogApp.d("log_tag", "${model.count} | ${model.price}")
        changeRowPrice(id = model.objectId, count = model.count, price = model.price)
    }

    fun handleOnClickOptionMenu(type: ActionButtonType) {
        when (type) {
            ActionButtonType.Back -> router?.popBackStack()
            ActionButtonType.Menu -> {}
            ActionButtonType.Delete -> deleteSelectItems()
        }
    }
     fun handleButtonClick(
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

enum class ActionButtonType(val icon: ImageVector, val color: Color) {
    Delete(Icons.Default.Delete, Color.Red),
    Menu(Icons.Default.Menu, Color.Gray),
    Back(Icons.Default.ArrowBack, Color.Gray)
}