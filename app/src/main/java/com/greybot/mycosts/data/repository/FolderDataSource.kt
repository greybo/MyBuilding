package com.greybot.mycosts.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.greybot.mycosts.data.dto.FolderDTO
import com.greybot.mycosts.utility.LogApp
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class FolderDataSource(coroutineContext: CoroutineContext = EmptyCoroutineContext) {

    private val scope = CoroutineScope(coroutineContext)

    private val uid: String = "123456"
    private val path: String = "explore"
    private val myRef = Firebase.database.reference.child(path).child(uid)

    suspend fun getFolderAll(): List<FolderDTO>? {
        val deferred = CompletableDeferred<List<FolderDTO>?>()
        myRef.orderByKey().addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val itemFolder = snapshot.children.mapNotNull {
                        it.getValue(FolderDTO::class.java)
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