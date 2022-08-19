package com.greybot.mycosts.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.greybot.mycosts.data.dto.FolderDTO
import com.greybot.mycosts.utility.LogApp

class FolderDataSource(uid: String = "123456") {
    private val path: String = "explore"
    private val myRef = Firebase.database.reference.child(path).child(uid)

    fun getFolderAll(callback: (List<FolderDTO>?) -> Unit) {
        myRef.orderByKey().addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val itemFolder = snapshot.children.mapNotNull {
                        it.getValue(FolderDTO::class.java)
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

    fun addFolder(item: FolderDTO) {
        val key = myRef.push().key ?: return
        item.objectId = key

        myRef.child(key).setValue(item) { error, ref ->
            if (error != null) {
                LogApp.e("ExploreFragment", error.toException())
            }
            LogApp.i("ExploreFragment", ref.toString())
        }
    }

}