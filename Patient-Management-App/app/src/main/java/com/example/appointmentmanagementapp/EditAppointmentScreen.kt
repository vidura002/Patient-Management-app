package com.example.appointmentmanagementapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.appointmentmanagementapp.Appointment_Data.Appointment
import com.example.appointmentmanagementapp.Appointment_Data.AppointmentDatabase
import com.example.appointmentmanagementapp.User_Data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class EditAppointmentScreen : AppCompatActivity() {

    private lateinit var patientID: EditText
    private lateinit var patientName: EditText
    private lateinit var contact: EditText
    private lateinit var date: EditText
    private lateinit var time: EditText

    private lateinit var uID: TextView

    private lateinit var saveChangesBtn: Button

    private lateinit var appointmentDB: AppointmentDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_appointment_screen)

        appointmentDB = AppointmentDatabase.getDatabase(this)

        patientID = findViewById(R.id.editTextTextPersonName6)

        patientID.isFocusable = false
        patientID.isFocusableInTouchMode = false
        patientID.isClickable = false

        patientName = findViewById(R.id.editTextTextPersonName2)
        contact = findViewById(R.id.editTextTextPersonName3)
        date = findViewById(R.id.editTextTextPersonName4)
        time = findViewById(R.id.editTextTextPersonName5)
        uID = findViewById(R.id.textView16)

        date.isFocusable = false
        date.isFocusableInTouchMode = false
        date.isClickable = false

        saveChangesBtn = findViewById(R.id.addAppointmentButton)

        uID.text = intent.getStringExtra("uid").toString()

        getAppointment(intent.getStringExtra("uid").toString())

        saveChangesBtn.setOnClickListener {
            updateAppointment()
        }

        time.isFocusable = false
        time.isFocusableInTouchMode = false
        time.isClickable = false

        time.setOnClickListener(View.OnClickListener {
            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour)
                    selectedTime.set(Calendar.MINUTE, selectedMinute)

                    // Format the selected time with AM/PM notation
                    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
                    val formattedTime = sdf.format(selectedTime.time)

                    // Update the EditText with the selected time in the new format
                    time.setText(formattedTime)
                },
                currentHour, currentMinute, false // false for 24-hour format
            )

            timePickerDialog.show()
        })

        date.setOnClickListener(View.OnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)

                    // Format the date with the month as a string
                    val monthName = SimpleDateFormat("MMMM", Locale.getDefault()).format(selectedDate.time)
                    val formattedDate = String.format("%02d %s %04d", selectedDay, monthName, selectedYear)

                    // Update the EditText with the selected date in the new format
                    date.setText(formattedDate)
                },
                year, month, day
            )

            datePickerDialog.show()
        })
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

    private fun updateAppointment(){
        val ePatientID = patientID.text.toString()
        val ePatientName = patientName.text.toString()
        val eContact = contact.text.toString()
        val eDate = date.text.toString()
        val eTime = time.text.toString()

        if (ePatientID.isEmpty() || ePatientName.isEmpty() || eContact.isEmpty() || eDate.isEmpty() || eTime.isEmpty()) {
            Toast.makeText(this, "Please Fill All Required Details!", Toast.LENGTH_SHORT).show()
        } else if (eContact.length > 10 || eContact.length < 10){
            Toast.makeText(this, "Please Enter Valid Phone Number!", Toast.LENGTH_SHORT).show()
        }
        else {

            try {
                GlobalScope.launch(Dispatchers.IO) {
                    appointmentDB.appointmentDao().updateAppointment(
                        intent.getStringExtra("uid").toString(),
                        ePatientName,
                        eContact,
                        eDate,
                        eTime,
                    )
                }

                Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, HomeScreen::class.java)
//                startActivity(intent)
//                finish()

            }catch (e: Error){
                e.printStackTrace()
                e.message?.let { Log.e("Update Error", it) }
            }

        }

    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

}