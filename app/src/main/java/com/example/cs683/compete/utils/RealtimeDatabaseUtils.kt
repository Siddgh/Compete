package com.example.cs683.compete.utils

import android.util.Log
import com.example.cs683.compete.constants.FirebaseConstants
import com.example.cs683.compete.models.LogEntries
import com.google.firebase.database.*
import kotlinx.coroutines.joinAll
import kotlin.math.max

object RealtimeDatabaseUtils {
    private val firebaseDatabaseInstance: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }

    val tasksDatabaseReference: DatabaseReference by lazy {
        firebaseDatabaseInstance.getReference(FirebaseConstants.tasksCollectionReference)
    }

    fun writeLogToRealtimeDatabase(taskId: String, userId: String, date: String, value: String) {

        tasksDatabaseReference.child(taskId).child(userId).child(FirebaseConstants.lastLogEntryKey)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val prevLog = snapshot.value.toString()
                        tasksDatabaseReference.child(taskId)
                            .child(userId)
                            .child(FirebaseConstants.lastLogEntryKey)
                            .setValue(max(date.toInt(), prevLog.toInt()))
                    } else {
                        tasksDatabaseReference.child(taskId)
                            .child(userId)
                            .child(FirebaseConstants.lastLogEntryKey)
                            .setValue(date)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        tasksDatabaseReference
            .child(taskId)
            .child(userId)
            .child(FirebaseConstants.entriesKey)
            .child(date)
            .setValue(value)
    }

    fun getLogEntriesFromCurrentUser(
        count: Int,
        taskId: String,
        onSuccess: (HashMap<Int, Double>) -> Unit
    ) {
        var counter = count
        val today = DateUtils.getTodaysDate()
        val valuesList = hashMapOf<Int, Double>()

        while (counter > 0) {
            val dateList = today.split('/')
            val dateVal = dateList[2] + dateList[0] + dateList[1]
            val value = dateVal.toInt() - counter
            valuesList[value] = 0.0
            counter -= 1
        }

        tasksDatabaseReference
            .child(taskId)
            .child(FirebaseAuthUtils.getUserMeta().uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        snapshot.children.forEach {
                            if (it.key == FirebaseConstants.lastLogEntryKey) {
                                // TODO
                            } else if (it.key == FirebaseConstants.entriesKey) {
                                it.children.forEach { dateEntry ->
                                    if (valuesList.keys.contains(dateEntry.key!!.toInt())) {
                                        valuesList[dateEntry.key!!.toInt()] =
                                            dateEntry.value.toString().toDouble()
                                    }
                                }
                            }
                        }
                        onSuccess(valuesList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    /*
    *
    * tasks
    *   - taskid
    *       - username
    *           - last_log_date : date
    *           - log_enteries
    *               - date_value : date_value
    *               - date_value2 : date_value2
    *
    *       - username
    *           - logs
    *
    *
    * */
}