package com.example.cs683.compete.utils

import android.content.Context
import android.view.Window
import android.widget.Toast
import com.google.android.material.elevation.SurfaceColors

object DesignUtils {
    fun blendStatusBar(context: Context, window: Window){
        val color = SurfaceColors.SURFACE_0.getColor(context)
        window.statusBarColor = color
        window.navigationBarColor = color
    }

    fun displayToast(context: Context,textToShow: String){
        Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show()
    }

}