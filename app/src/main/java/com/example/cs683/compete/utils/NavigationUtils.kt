package com.example.cs683.compete.utils

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.example.cs683.compete.activities.Home
import com.example.cs683.compete.activities.TasksDetail
import com.example.cs683.compete.fragments.AddTaskBottomSheet
import com.example.cs683.compete.constants.GenericConstants
import com.example.cs683.compete.models.Task

object NavigationUtils {

    fun showAddTaskBottomSheet(
        supportFragmentManager: FragmentManager,
        addTaskBottomSheet: AddTaskBottomSheet
    ) {
        addTaskBottomSheet.show(supportFragmentManager, addTaskBottomSheet.tag)
    }

    fun goToHome(context: Context) {
        val intent = Intent(context, Home::class.java)
        context.startActivity(intent)
    }

    fun goToTaskList(context: Context, task: Task) {
        val intent = Intent(context, TasksDetail::class.java)
        intent.putExtra(GenericConstants.TASK_TITLE, task.taskTitle)
        intent.putExtra(GenericConstants.TASK_METRIC, task.taskMetric!!)
        intent.putExtra(GenericConstants.TASK_TYPE, task.taskType!!)
        intent.putExtra(GenericConstants.TASK_ID, task.taskId!!)
        context.startActivity(intent)
    }

    fun parseTaskDataFromIntent(taskTitle: String, taskMetric: Int, taskType: Int, taskId: String): Task {
        return Task(
            taskMetric = taskMetric,
            taskTitle = taskTitle,
            taskType = taskType,
            taskId = taskId
        )
    }

}