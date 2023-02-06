package com.greybot.mycosts.data.repository.explore

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.greybot.mycosts.data.dto.ExploreRow
import com.greybot.mycosts.utility.LogApp
import com.greybot.mycosts.utility.toastDebug
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

//@Singleton
class ExploreRepository @Inject constructor() {

    private val uid: String = "654321"
    private val path: String = "exploreNew"
    private val myRef = Firebase.database.reference.child(uid).child(path)
//    val backupList = mutableListOf<ExploreRow>()

    fun getAllData(success: (List<ExploreRow>) -> Unit, failed: (Throwable) -> Unit) {
        myRef.orderByKey().addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val itemExplore = snapshot.children.mapNotNull {
                        it.getValue(ExploreRow::class.java)
                    }
                    success.invoke(itemExplore)
                }

                override fun onCancelled(error: DatabaseError) {
                    failed.invoke(error.toException())
                    LogApp.e("getFolderTest", error.toException())
                    toastDebug = error.message
                }
            }
        )
    }

    suspend fun addFolder(item: ExploreRow): ExploreRow? {
        return suspendCoroutine {
            val key = myRef.push().key ?: return@suspendCoroutine it.resume(null)
            item.objectId = key
//        backupList.add(item)
            myRef.child(key).setValue(item) { error, ref ->
                val result = if (error != null) {
                    LogApp.e("ExploreFragment", error.toException())
                    null
                } else {
                    LogApp.i("ExploreFragment", ref.toString())
                    item
                }
                it.resume(result)
            }
        }
    }

    fun update(item: ExploreRow) {
        val database: DatabaseReference = Firebase.database.reference

        if (item.objectId == null) {
            LogApp.w("TAG", null, "Couldn't get push key for posts")
            return
        }
//        backupList.forEachIndexed { index, model ->
//            if (model.objectId == item.objectId) {
//                backupList[index] = item
//            }
//        }
        val dtoMap = item.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/$uid/$path/${item.objectId}" to dtoMap
        )

        database.updateChildren(childUpdates)
//            .addOnSuccessListener {
//                LogApp.i("writeNewPost", "success")
//            }
        /*.addOnFailureListener {
                LogApp.e("writeNewPost", it)
            }*/
    }


    private fun equalsList(
        newList: List<ExploreRow>,
        backupList: MutableList<ExploreRow>
    ): Boolean {
        if (newList.size != backupList.size) return false
        newList.forEachIndexed { index, dto ->
            if (dto != backupList.getOrNull(index)) return false
        }
        return true
    }
}