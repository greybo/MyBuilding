package com.greybot.mycosts.present.file.add

import android.text.Editable
import androidx.lifecycle.SavedStateHandle
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.CurrencyDto
import com.greybot.mycosts.data.dto.FolderRow
import com.greybot.mycosts.data.repository.file.FileDataSource
import com.greybot.mycosts.data.repository.folder.FolderDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RowAddViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val fileSource: FileDataSource,
    private val exploreSource: FolderDataSource
) : CompositeViewModel() {

    private var folderRow: FolderRow? = null
    private val objectId: String
        get() = savedStateHandle.get<String>("objectId") ?: throw Throwable()

    fun fetchData() {
        launchOnDefault {
            folderRow = exploreSource.findByObjectId(objectId)
        }
    }

    fun addRow(
        rowName: String,
        count: Editable?,
        price: Editable?,
        currency: CurrencyDto? = null,
        parentId: String? = objectId
    ) {
        launchOnDefault {
            val _count = count.toString().toDouble()
            val _price = price.toString().toDouble()

            fileSource.addFile(rowName, _count, _price, currency, parentId)
            folderRow?.let {
                exploreSource.updateFolder(it.copy(files = true))
            } ?: throw Throwable()
        }
    }
}