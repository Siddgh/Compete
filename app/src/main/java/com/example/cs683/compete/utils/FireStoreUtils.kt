package com.example.cs683.compete.utils

import android.util.Log
import com.example.cs683.compete.constants.FirebaseConstants
import com.example.cs683.compete.constants.GenericConstants
import com.example.cs683.compete.models.Task
import com.example.cs683.compete.models.TaskLog
import com.example.cs683.compete.models.User
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source

object FireStoreUtils {

    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    val tasksCollectionReference: CollectionReference by lazy {
        firestoreInstance.collection(FirebaseConstants.usersCollectionReference)
            .document(FirebaseAuthUtils.getUserMeta().uid!!)
            .collection(FirebaseConstants.tasksCollectionReference)
    }

    val getTasksQuery: Query by lazy {
        firestoreInstance
            .collection(FirebaseConstants.usersCollectionReference)
            .document(FirebaseAuthUtils.getUserMeta().uid!!)
            .collection(FirebaseConstants.tasksCollectionReference)
    }

    fun addTaskToFireStore(taskMeta: Task) {

        getTasksQuery.count().get(AggregateSource.SERVER).addOnCompleteListener { task ->


            if (task.isSuccessful) {
                val snapshot = task.result
                val count = snapshot.count
                val key = "task${count + 1}"
                taskMeta.taskId = key
                firestoreInstance
                    .collection(FirebaseConstants.usersCollectionReference)
                    .document(FirebaseAuthUtils.getUserMeta().uid!!)
                    .collection(FirebaseConstants.tasksCollectionReference)
                    .document(key)
                    .set(taskMeta)
            }
        }
    }

    fun addUserToFireStore(user: User) {
        firestoreInstance.collection(FirebaseConstants.usersCollectionReference)
            .document(user.uid!!).set(user)
    }

    fun addTaskLogToFireStore(value: String, date: String, taskMeta: Task) {
        tasksCollectionReference.document(taskMeta.taskId!!).collection(date)
            .document(FirebaseAuthUtils.getUserMeta().uid!!)
            .set(TaskLog(value.toString(), FirebaseAuthUtils.getUserMeta()))

        RealtimeDatabaseUtils.writeLogToRealtimeDatabase(
            taskId = taskMeta.taskId.toString(),
            userId = FirebaseAuthUtils.getUserMeta().uid.toString(),
            date = date,
            value = value
        )
    }

    fun getLoggedValuesForCurrentUser(count: Int, taskMeta: Task) {
        var counter = count
        tasksCollectionReference.document(taskMeta.taskId!!)
    }

}