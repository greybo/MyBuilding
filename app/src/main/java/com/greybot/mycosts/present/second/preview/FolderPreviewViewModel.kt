package com.greybot.mycosts.present.second.preview

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

    private val fileHandler by lazy { FileHandler() }
    var parentFolder: ExploreRow? = null
    val state = makeLiveData<List<AdapterItems>>()
    val title = makeLiveData<String?>()

    val parentId by lazy { savedStateHandle.get<String>("objectId") ?: "" }

    init {
        launchOnDefault {
            parentFolder = exploreSource.findByObjectId(parentId)
            title.postValue(parentFolder?.name)
        }
    }

    fun fetchData(id: String = parentId) {
        launchOnDefault {
            val total = ItemTotalHelper(exploreSource.fetchData(), rowSource.fetch())
            val folderHandler = FolderHandler(total)
            val folderList = exploreSource.findByParentId(id)
            val files = rowSource.findByParentId(id)
            if (!folderList.isNullOrEmpty()) {
                makeFolderList(folderList, folderHandler)
            } else if (files.isNotEmpty()) {
                makeFileList(files)
            } else makeButtonList()
        }
    }

    private fun makeFolderList(list: List<ExploreRow>, folderHandler: FolderHandler) {
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
    Folder("Папка"), Row("Товар"), None("")
}