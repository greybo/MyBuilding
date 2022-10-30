package com.greybot.mycosts.present.second.preview

import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.data.repository.ExploreRepository
import com.greybot.mycosts.models.AdapterItems
import com.greybot.mycosts.present.row.RowHandler2
import com.greybot.mycosts.present.second.FolderHandler2
import com.greybot.mycosts.utility.myLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderPreviewViewModel @Inject constructor(private val exploreRepo: ExploreRepository) :
    CompositeViewModel() {

    private val rowHandler by lazy { RowHandler2() }
    private val folderHandler by lazy { FolderHandler2() }
    val state = myLiveData<List<AdapterItems>>()

    var objectId: String = ""
    var path: String? = null

    fun fetchData(objectId: String?, path: String?) {
        this.objectId = objectId ?: ""
        this.path = path
        launchOnDefault {
            val folder = exploreRepo.findById(this@FolderPreviewViewModel.objectId)
            val rows = folder?.files?.filter { it.path?.startsWith(path ?: "") == true }
            handleResult(rows)
        }
    }

    fun changeRowBuy(item: AdapterItems.RowItem) {
//        rowDataSource.changeBuyStatus(item.objectId)
//        makeRowList(rowDataSource.geBackupList())
    }

    private fun handleResult(list: List<FileRow>?) {
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

    private fun makeFolderList(list: List<FileRow>): List<AdapterItems> {
        return folderHandler.makeFolderItems(list)
    }
}

enum class ButtonType(val row: String) {
    Folder("Folder"), Row("Row"), None("")
}