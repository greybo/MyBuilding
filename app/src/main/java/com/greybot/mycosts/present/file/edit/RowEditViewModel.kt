package com.greybot.mycosts.present.file.edit

import androidx.lifecycle.MutableLiveData
import com.greybot.mycosts.base.CompositeViewModel
import com.greybot.mycosts.data.dto.RowDto
import com.greybot.mycosts.data.repository.row.RowDataSource

class RowEditViewModel : CompositeViewModel() {

    private val dataSource = RowDataSource()
    val status = MutableLiveData<RowDto?>()
    fun fetchData(objectId: String?) {
        objectId ?: throw Throwable()
        launchOnDefault {
            val model = dataSource.findById(objectId)
            makeItems(model)
        }
    }
    private fun makeItems(model: RowDto?) {
        status.postValue(model)
    }
}

