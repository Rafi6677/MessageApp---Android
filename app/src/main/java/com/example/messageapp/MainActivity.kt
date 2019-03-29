package com.example.messageapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerButton.setOnClickListener {
            performRegister()
        }

        hasAccountTextView.setOnClickListener {
            Log.d("MainActivity", "Login activity")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performRegister() {
        val email = emailInputRegister.text.toString()
        val password = passwordInputRegister.text.toString()

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Wszystkie pola muszą być wypełnione.", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
            }
            .addOnFailureListener {
                Log.d("Main", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Pola wypełnione niepoprawnie.", Toast.LENGTH_SHORT).show()
            }
    }
}
