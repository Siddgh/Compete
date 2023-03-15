package com.example.cs683.compete.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cs683.compete.R
import com.example.cs683.compete.models.User
import com.google.firebase.auth.FirebaseAuth


object FirebaseAuthUtils {

    fun getUserMeta(): User {
        val user = FirebaseAuth.getInstance().currentUser
        return User(
            user?.displayName,
            user?.email,
            user?.photoUrl.toString(),
            user?.uid
        )
    }

    fun writeUserToFireStore(user: User) {
        FireStoreUtils.addUserToFireStore(user)
    }

    fun setUserProfile(userName: TextView, imageView: ImageView, context: Context) {
        userName.text = getUserMeta().displayName
        Glide.with(context).load(getUserMeta().photoUrl).placeholder(R.drawable.temp_profile1)
            .into(imageView)
    }
}