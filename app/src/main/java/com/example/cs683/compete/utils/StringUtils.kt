package com.example.cs683.compete.utils
import android.util.Log
import com.example.cs683.compete.constants.GenericConstants

object StringUtils {
    fun getTaskTypeIdFromTaskTypeName(taskTypeName: String): Int{
        var taskType = 0
        when(taskTypeName){
            GenericConstants.TASK_TYPE_SELF -> taskType = 0
            GenericConstants.TASK_TYPE_FRIENDS -> taskType = 1
            GenericConstants.TASK_TYPE_MENTOR -> taskType = 2
        }
        return taskType
    }

    fun getTaskTypeNameFromTaskTypeId(taskTypeId: Int): String{
        var taskType = ""
        when(taskTypeId){
            0 -> taskType = GenericConstants.TASK_TYPE_SELF
            1 -> taskType = GenericConstants.TASK_TYPE_FRIENDS
            2 -> taskType = GenericConstants.TASK_TYPE_MENTOR
        }
        return taskType
    }

    fun getTaskMetricIdFromTaskMetricName(taskMetricName: String): Int{
        var taskMetric = 0
        when(taskMetricName){
            GenericConstants.TASK_METRIC_NUMBERS -> taskMetric = 0
            GenericConstants.TASK_METRIC_KILOMETERS -> taskMetric = 1
            GenericConstants.TASK_METRIC_MILES -> taskMetric = 2
            GenericConstants.TASK_METRIC_TIME -> taskMetric = 3
        }
        return taskMetric
    }

    fun getTaskMetricNameFromTaskMetricId(taskMetricId: Int): String{
        var taskMetricName = ""
        when(taskMetricId){
            0 -> taskMetricName = GenericConstants.TASK_METRIC_NUMBERS
            1 -> taskMetricName = GenericConstants.TASK_METRIC_KILOMETERS
            2 -> taskMetricName = GenericConstants.TASK_METRIC_MILES
            3 -> taskMetricName = GenericConstants.TASK_METRIC_TIME
        }
        return taskMetricName
    }

}