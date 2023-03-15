package com.example.cs683.compete

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cs683.compete.auth.SignInActivity
import com.example.cs683.compete.databinding.ActivitySignUpBinding
import com.example.cs683.compete.models.User
import com.example.cs683.compete.utils.FirebaseAuthUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()
            val userName = binding.usernameEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty() && userName.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {

                            val profileUpdates =
                                UserProfileChangeRequest.Builder().setDisplayName(userName).build()

                            val userFirebaseInstance = FirebaseAuth.getInstance().currentUser

                            userFirebaseInstance!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
                                if (task.isSuccessful){
                                    val user = User(
                                        userFirebaseInstance.displayName,
                                        userFirebaseInstance.email,
                                        userFirebaseInstance.photoUrl.toString(),
                                        userFirebaseInstance.uid
                                    )

                                    FirebaseAuthUtils.writeUserToFireStore(user = user)

                                    val intent = Intent(this, SignInActivity::class.java)
                                    startActivity(intent)

                                }
                            }
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }
}