package com.example.messageapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()


        }

        hasAccountTextView.setOnClickListener {
            Log.d("MainActivity", "Login activity")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
