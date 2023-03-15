package com.example.cs683.compete.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun getTodaysDate(): String {
        val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        return simpleDateFormat.format(Date())
    }

    fun getMonthFromDateString(fullDate: String): String {
        return fullDate.split("/")[0]
    }

    fun getYearFromDateString(fullDate: String): String {
        return fullDate.split("/")[2]
    }

    fun getDayFromDateString(fullDate: String): String {
        return fullDate.split("/")[1]
    }

    fun getYYYYMMDDFromDateString(fullDate: String): String {
        var day = fullDate.split("/")[1]
        if (day.length < 2) {
            day = "0${day}"
        }

        var month = fullDate.split("/")[0]
        if (month.length < 2) {
            month = "0${month}"
        }

        val year = fullDate.split("/")[2]
        return "${year}${month}${day}"
    }

}