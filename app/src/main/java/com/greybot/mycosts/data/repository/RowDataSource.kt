package com.greybot.mycosts.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.greybot.mycosts.data.dto.RowDto
import com.greybot.mycosts.utility.LogApp
import kotlinx.coroutines.CompletableDeferred

class RowDataSource {
    private val uid: String = "123456"
    private val path: String = "rows"
    private val myRef = Firebase.database.reference.child(path).child(uid)

    suspend fun getAllData(): List<RowDto>? {
        val deferred = CompletableDeferred<List<RowDto>?>()
        myRef.orderByKey().addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val itemFolder = snapshot.children.mapNotNull {
                        it.getValue(RowDto::class.java)
                    }
                    deferred.complete(itemFolder)
                }

                override fun onCancelled(error: DatabaseError) {
                    deferred.completeExceptionally(error.toException())
                    LogApp.e("getFolderTest", error.toException())
                }
            }
        )
        return deferred.await()
    }

    suspend fun getById(objectId: String): RowDto? {
        val deferred = CompletableDeferred<RowDto?>()
        myRef.child(objectId).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val itemRow = snapshot.getValue(RowDto::class.java)
                    deferred.complete(itemRow)
                }

                override fun onCancelled(error: DatabaseError) {
                    deferred.completeExceptionally(error.toException())
                    LogApp.e("getFolderTest", error.toException())
                }
            }
        )
        return deferred.await()
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