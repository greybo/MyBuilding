package com.greybot.mycosts.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.greybot.mycosts.data.dto.RowDto
import com.greybot.mycosts.utility.LogApp

class RowDataSource(uid: String = "123456") {

    private val path: String = "rows"
    private val myRef = Firebase.database.reference.child(path).child(uid)

    fun getAllData(callback: (List<RowDto>?) -> Unit) {
        myRef.orderByKey().addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val itemFolder = snapshot.children.mapNotNull {
                        it.getValue(RowDto::class.java)
                    }
                    callback.invoke(itemFolder)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback.invoke(null)
                    LogApp.e("getFolderTest", error.toException())
                }
            }
        )
    }

    fun addRow(dto: RowDto) {
        val key = myRef.push().key ?: return
        dto.objectId = key

        myRef.child(key).setValue(dto) { error, ref ->
            if (error != null) {
                LogApp.e("ExploreFragment", error.toException())
            }
            LogApp.i("ExploreFragment", ref.toString())
        }
    }

}