package com.greybot.mycosts.present.second.preview

import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.ExploreRow
import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.data.repository.explore.ExploreDataSource
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.row.RowHandler2
import com.greybot.mycosts.present.second.FolderHandler2
import com.greybot.mycosts.utility.myLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderPreviewViewModel @Inject constructor(private val exploreSource: ExploreDataSource) :
    CompositeViewModel() {

    private var folder: ExploreRow? = null
    private val rowHandler by lazy { RowHandler2() }
    private val folderHandler by lazy { FolderHandler2() }
    val state = myLiveData<List<AdapterItems>>()

    var objectId: String = ""

    fun fetchData(objectId: String) {
        this.objectId = objectId
        launchOnDefault {
            folder = exploreSource.findParent(objectId)
            if (folder?.isFiles == true){
                TODO("implement file logic")
            }else{
                val folderList = exploreSource.findChildren(objectId)
//                handleResult(folderList)
                makeFolderList(folderList)
            }
        }
    }

    fun changeRowBuy(item: AdapterItems.RowItem) {
//        rowDataSource.changeBuyStatus(item.objectId)
//        makeRowList(rowDataSource.geBackupList())
    }

    private fun handleResult(list: List<ExploreRow>?) {
        val groupedByFile = list?.groupBy { it.isFile }
        val file = groupedByFile?.get(true)
        val folder = groupedByFile?.get(false)

        val items = if (!folder.isNullOrEmpty()) {
            makeFolderList(folder)
        } else if (!file.isNullOrEmpty()) {
            makeFileList(file)
        } else {
            makeButtonList()
        }

        state.postValue(items)
    }

    private fun makeButtonList(): List<AdapterItems> {
        return listOf(
            AdapterItems.ButtonAddItem(ButtonType.Folder),
            AdapterItems.ButtonAddItem(ButtonType.Row)
        )
    }

    private fun makeFileList(rowList: List<FileRow>?): List<AdapterItems> {
        rowList ?: return emptyList()
        return rowHandler.makeGroupBuy(rowList)
    }

    private fun makeFolderList(list: List<ExploreRow>): List<AdapterItems> {
        return folderHandler.makeFolderItems(list)
    }
}

enum class ButtonType(val row: String) {
    Folder("Folder"), Row("Row"), None("")
}