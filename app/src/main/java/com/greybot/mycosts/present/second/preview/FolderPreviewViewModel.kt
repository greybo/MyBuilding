package com.greybot.mycosts.present.second.preview

import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.ExploreRow
import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.data.repository.explore.ExploreDataSource
import com.greybot.mycosts.data.repository.row.FileDataSource
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.file.RowHandler2
import com.greybot.mycosts.present.second.FolderHandler2
import com.greybot.mycosts.utility.myLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderPreviewViewModel @Inject constructor(
    private val exploreSource: ExploreDataSource,
    private val rowSource: FileDataSource
) : CompositeViewModel() {

    private var parentFolder: ExploreRow? = null
    private val rowHandler by lazy { RowHandler2() }
    private val folderHandler by lazy { FolderHandler2() }
    val state = myLiveData<List<AdapterItems>>()
    val title = myLiveData<String?>()

    val objectId: String get() = parentFolder?.objectId ?: ""

    fun fetchData(objectId: String) {
        launchOnDefault {
            val folder = exploreSource.findFolderModels(objectId)
            parentFolder = folder.parent
            title.postValue(parentFolder?.name)
            if (folder.children.isNotEmpty()) {
                makeFolderList(folder.children)
            } else if (folder.ifFiles()) {
                val files = rowSource.getAllByParent(objectId)
                makeFileList(files)
            } else {
                makeButtonList()
            }
        }
    }

    fun changeRowBuy(item: AdapterItems.RowItem) {
        rowSource.changeBuyStatus(item.objectId)
        makeFileList(rowSource.geBackupList())
    }

    private fun makeButtonList() {
        setToLiveData = listOf(
            AdapterItems.ButtonAddItem(ButtonType.Folder),
            AdapterItems.ButtonAddItem(ButtonType.Row)
        )
    }

    private fun makeFileList(rowList: List<FileRow>?) {
        rowList ?: return
        setToLiveData = rowHandler.makeGroupBuy(rowList)
    }

    private fun makeFolderList(list: List<ExploreRow>) {
        setToLiveData = folderHandler.makeFolderItems(list)
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