package com.example.appointmentmanagementapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var login: Button
    private lateinit var register: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login = findViewById(R.id.button2)
        register = findViewById(R.id.buttonSignUp)

        login.setOnClickListener {
            intent = Intent(applicationContext, LoginScreen::class.java)
            startActivity(intent)
        }

        register.setOnClickListener {
            intent = Intent(applicationContext, RegisterScreen::class.java)
            startActivity(intent)
        }
    }
}