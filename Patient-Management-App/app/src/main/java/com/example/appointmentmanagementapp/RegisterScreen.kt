package com.example.appointmentmanagementapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.appointmentmanagementapp.User_Data.User
import com.example.appointmentmanagementapp.User_Data.UserDatabase
import com.example.appointmentmanagementapp.databinding.ActivityRegisterScreenBinding
import kotlinx.coroutines.*

class RegisterScreen : AppCompatActivity() {

    private lateinit var patientName : EditText
    private lateinit var address: EditText
    private lateinit var patientID: EditText
    private lateinit var password: EditText

    private lateinit var registerBtn: Button
    private lateinit var goToLogin: TextView

    private lateinit var appDB: UserDatabase

    private lateinit var alertText: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)

        appDB = UserDatabase.getDatabase(this)

        patientName = findViewById(R.id.editTextTextPersonName2)
        address = findViewById(R.id.editTextTextPersonName3)
        patientID = findViewById(R.id.editTextTextPersonName4)
        password = findViewById(R.id.editTextTextPersonName5)

        registerBtn = findViewById(R.id.button)
        goToLogin = findViewById(R.id.textView7)

        alertText = findViewById(R.id.textView8)

        registerBtn.setOnClickListener {
            validatePatientID()
        }

        goToLogin.setOnClickListener{
            intent = Intent(applicationContext, LoginScreen::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser(){
        val ePatientName = patientName.text.toString()
        val eAddress = address.text.toString()
        val ePatientID = patientID.text.toString()
        val ePassword = password.text.toString()

        if (ePatientName.isEmpty() || eAddress.isEmpty() || ePatientID.isEmpty() || ePassword.isEmpty()) {
            Toast.makeText(this, "Please Fill All Required Details!", Toast.LENGTH_SHORT).show()
        }else {
            val user = User(
                null, ePatientID, ePatientName, eAddress, ePassword
            )
            GlobalScope.launch(Dispatchers.IO) {
                appDB.userDao().insert(user)
            }

            Toast.makeText(this, "Registered Successful", Toast.LENGTH_SHORT).show()
            intent = Intent(applicationContext, LoginScreen::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun validatePatientID(){

        val lPatientID = patientID.text.toString()

        GlobalScope.launch(Dispatchers.IO) {
            val isPatientIDAvailable = appDB.userDao().isPatientIDValid(lPatientID)
            if (isPatientIDAvailable == 1) {
                runOnUiThread {
                    alertText.text = "Patient ID Already Exits! Please try Another ID."
                }
            } else {
                runOnUiThread {
                    registerUser()
                }
            }
        }

    }

}