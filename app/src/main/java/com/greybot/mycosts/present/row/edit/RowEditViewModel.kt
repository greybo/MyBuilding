package com.greybot.mycosts.present.row.edit

import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.data.repository.row.FileDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RowEditViewModel @Inject constructor(private val dataSource: FileDataSource) : CompositeViewModel() {

    val status = MutableLiveData<FileRow?>()
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
        dataSource.save(editModel)
    }
}

