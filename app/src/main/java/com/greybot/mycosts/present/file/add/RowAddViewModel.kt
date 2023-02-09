package com.greybot.mycosts.present.file.add

import androidx.lifecycle.SavedStateHandle
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.CurrencyDto
import com.greybot.mycosts.data.dto.ExploreRow
import com.greybot.mycosts.data.repository.explore.FolderDataSource
import com.greybot.mycosts.data.repository.row.FileDataSource
import com.greybot.mycosts.utility.LogApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RowAddViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val fileSource: FileDataSource,
    private val exploreSource: FolderDataSource
) : CompositeViewModel() {

    private var exploreRow: ExploreRow? = null
    private val objectId: String
        get() = savedStateHandle.get<String>("objectId") ?: throw Throwable()

    fun fetchData() {
        launchOnDefault {
            exploreRow = exploreSource.findByObjectId(objectId)
        }
    }

    fun addRow(
        rowName: String,
        count: String = "1",
        price: Float = 0F,
        currency: CurrencyDto? = null,
        parentId: String? = objectId
    ) {
        launchOnDefault {
            var _count = try {
                count.toInt()
            } catch (e: Exception) {
                LogApp.w("addRow_tag", e)
                1
            }
            if (_count == 0) {
                _count = 1
            }
            fileSource.addFile(rowName, _count, price, currency, parentId)
            exploreRow?.let {
                exploreSource.updateFolder(it.copy(files = true))
            } ?: throw Throwable()
        }
    }
}