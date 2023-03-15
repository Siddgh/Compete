package com.example.cs683.compete

import android.app.Application
import com.google.android.material.color.DynamicColors

class Compete : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}