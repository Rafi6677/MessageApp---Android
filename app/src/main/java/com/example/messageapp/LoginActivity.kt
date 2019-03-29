package com.example.messageapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener {
            performLogin()
        }

        backToRegistrationTextView.setOnClickListener {
            finish()
        }
    }

    private fun performLogin(){
        val email = emailInputLogin.text.toString()
        val password = passwordInputLogin.text.toString()

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Wszystkie pola muszą być wypełnione.", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
            }
            .addOnFailureListener {
                Toast.makeText(this, "Podany email lub hasło są nieprawidłowe.", Toast.LENGTH_SHORT).show()
            }
    }

}