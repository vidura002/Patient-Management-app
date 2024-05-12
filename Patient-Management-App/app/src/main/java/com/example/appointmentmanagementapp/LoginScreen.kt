package com.example.appointmentmanagementapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.appointmentmanagementapp.User_Data.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginScreen : AppCompatActivity() {

    private lateinit var patientID : EditText
    private lateinit var password : EditText

    private lateinit var loginBtn: Button
    private lateinit var goToRegister: TextView
    private lateinit var alertText: TextView

    private lateinit var appDB: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        appDB = UserDatabase.getDatabase(this)

        patientID = findViewById(R.id.editTextTextPersonName2)
        password = findViewById(R.id.editTextTextPersonName3)
        alertText = findViewById(R.id.textView8)

        loginBtn = findViewById(R.id.button)
        goToRegister = findViewById(R.id.textView7)

        loginBtn.setOnClickListener {
            validateUser()
        }

        goToRegister.setOnClickListener{
            intent = Intent(applicationContext, RegisterScreen::class.java)
            startActivity(intent)
        }

    }

    private fun validateUser(){

        val lPatientID = patientID.text.toString()
        val lPassword = password.text.toString()

        GlobalScope.launch(Dispatchers.IO) {
            val isPasswordCorrect = appDB.userDao().isPasswordCorrect(lPatientID, lPassword)
            if (isPasswordCorrect == 1) {
                println("Password Correct")
                runOnUiThread {
                    passwordCorrect()
                }
            } else {
                // Password is incorrect
                runOnUiThread {
                    alertText.text = "Wrong ID or Password! Please try Again."
                }
                println("Password Wrong")
            }
        }

    }

     private fun passwordCorrect(){
        Toast.makeText(applicationContext, "Login Success!", Toast.LENGTH_SHORT).show()
        intent = Intent(applicationContext, HomeScreen::class.java)
         intent.putExtra("Patient_ID", patientID.text.toString())
        startActivity(intent)
        finish()
    }


}