package com.greybot.mycosts.present.file.edit

import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.data.repository.file.FileDataSource
import com.greybot.mycosts.utility.makeLiveData
import com.greybot.mycosts.utility.round2Double
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RowEditViewModel @Inject constructor(private val dataSource: FileDataSource) :
    CompositeViewModel() {

    val status = makeLiveData<FileRow?>()
    private val fileModel get() = status.values
    fun fetchData(objectId: String?) {
        objectId ?: throw Throwable()
        launchOnDefault {
            val model = dataSource.findById(objectId)
            makeItems(model)
        }
    }

    private fun makeItems(model: FileRow?) {
        status.postValue(model)
    }

    fun editRow(editModel: FileRow?) {
        launchOnDefault {
            dataSource.update(editModel)
        }
    }

    fun update(rowName: String, count: String, price: String) {
        val _count = count.round2Double() ?: 0.0 // ifBlank { 0f }.toString().toFloat()
        val _price = price.round2Double() ?: 0.0// ifBlank { 0F }.toString().toFloat()

        val editModel = fileModel?.copy(
            name = rowName,
            count = _count,
            price = _price,
        )
        launchOnDefault {
            dataSource.update(editModel)
        }
    }
}

