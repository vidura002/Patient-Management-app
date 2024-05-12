package com.example.appointmentmanagementapp

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.appointmentmanagementapp.Appointment_Data.Appointment
import com.example.appointmentmanagementapp.Appointment_Data.AppointmentDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewAppointmentScreen : AppCompatActivity() {

    private lateinit var patientID: TextView
    private lateinit var patientName: TextView
    private lateinit var contact: TextView
    private lateinit var date: TextView
    private lateinit var time: TextView

    private lateinit var uID: TextView

    private lateinit var deleteBtn: Button

    private lateinit var appointmentDB: AppointmentDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_appointment_screen)

        appointmentDB = AppointmentDatabase.getDatabase(this)

        patientID = findViewById(R.id.editTextTextPersonName6)
        patientName = findViewById(R.id.editTextTextPersonName2)
        contact = findViewById(R.id.editTextTextPersonName3)
        date = findViewById(R.id.editTextTextPersonName4)
        time = findViewById(R.id.editTextTextPersonName5)
        uID = findViewById(R.id.textView16)

        deleteBtn = findViewById(R.id.deleteAppointmentButton)

        uID.text = intent.getStringExtra("uid").toString()

        getAppointment(intent.getStringExtra("uid").toString())

        deleteBtn.setOnClickListener {
            deleteApp(intent.getStringExtra("uid").toString())
        }
    }

    private suspend fun setAppointmentData(appointment: Appointment){


        withContext(Dispatchers.Main){
            patientID.text = appointment.aPatientID?.toEditable() ?: null
            contact.text = appointment.aContact?.toEditable() ?: null
            patientName.text = appointment.aName?.toEditable() ?: null
            date.text = appointment.aDate?.toEditable() ?: null
            time.text = appointment.aTime?.toEditable() ?: null
        }
    }

    private fun getAppointment(iid: String){

        var appointment: Appointment

        GlobalScope.launch{

            appointment = appointmentDB.appointmentDao().getSingleAppointment(iid)
            setAppointmentData(appointment)
        }
    }

    private fun deleteApp(dID :String){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Do you want to Delete?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){ _: DialogInterface, _: Int ->
            GlobalScope.launch(Dispatchers.IO) {

                appointmentDB = AppointmentDatabase.getDatabase(applicationContext)

                appointmentDB.appointmentDao().deleteAppointment(dID)

                finish()

            }
        }

        builder.setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }

        builder.show()
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

}

