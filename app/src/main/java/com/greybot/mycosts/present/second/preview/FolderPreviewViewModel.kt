package com.greybot.mycosts.present.second.preview

import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.ExploreRow
import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.data.repository.explore.ExploreDataSource
import com.greybot.mycosts.data.repository.row.FileDataSource
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.file.FileHandler
import com.greybot.mycosts.present.second.FolderHandler
import com.greybot.mycosts.utility.makeLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderPreviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val exploreSource: ExploreDataSource,
    private val rowSource: FileDataSource
) : CompositeViewModel() {

    var parentFolder: ExploreRow? = null
    private val fileHandler by lazy { FileHandler() }
    private val folderHandler by lazy { FolderHandler() }
    val state = makeLiveData<List<AdapterItems>>()
    val title = makeLiveData<String?>()

    val parentId = savedStateHandle.get<String>("objectId") ?: ""
    private val exploreObserver = Observer<Map<String, List<ExploreRow>>> {
        fetchData()
    }

    init {
        exploreSource.listLiveData.observeForever(exploreObserver)
        launchOnDefault {
            parentFolder = exploreSource.findByObjectId(parentId)
            title.postValue(parentFolder?.name)
        }
    }

    override fun onCleared() {
        super.onCleared()
        exploreSource.listLiveData.removeObserver(exploreObserver)
    }

    fun fetchData(id: String = parentId) {
        launchOnDefault {
            val folderList = exploreSource.findByParentId(id)
            val files = rowSource.findByParentId(id)

            if (!folderList.isNullOrEmpty()) {
                makeFolderList(folderList)
            } else if (files.isNotEmpty()) {
                makeFileList(files)
            } else makeButtonList()
        }
    }

    private fun makeFolderList(list: List<ExploreRow>) {
        setToLiveData = folderHandler.makeFolderItems(list)
    }

    private fun makeFileList(rowList: List<FileRow>) {
        setToLiveData = fileHandler.makeGroupBuy(rowList)
    }

    fun changeRowBuy(item: AdapterItems.RowItem) {
        rowSource.changeBuyStatus(item.objectId)
        launchOnDefault {
            updateUIRowList()
        }
    }

    private suspend fun updateUIRowList() {
        val files = rowSource.findByParentId(parentId)
        makeFileList(files)
    }

    fun changeRowPrice(id: String, count: Int, price: Float) {
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

    private var setToLiveData: List<AdapterItems>
        get() = state.values
        set(value) {
            state.postValue(value)
        }
}

enum class ButtonType(val row: String) {
    Folder("Folder"), Row("Row"), None("")
}