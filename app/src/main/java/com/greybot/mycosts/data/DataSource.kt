package com.greybot.mycosts.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.greybot.mycosts.utility.LogApp


class DataSource {

    private val myReExplore: DatabaseReference by lazy { Firebase.database.reference.child("explore") }
    private val database: DatabaseReference by lazy { Firebase.database.reference }
    val exploreList: List<String> = fakeData
    val tag = "DataSource"


    fun getAllData() {
        // Read from the database
        // Read from the database
        myReExplore.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                LogApp.i(tag, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                LogApp.w(tag, error.toException(), "Failed to read value.")
            }
        })
    }

    fun saveExplore() {
        val key = database.child("explore").push().key

    }

}