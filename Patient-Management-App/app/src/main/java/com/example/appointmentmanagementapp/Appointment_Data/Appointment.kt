package com.example.appointmentmanagementapp.Appointment_Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val uid: Int?,

    @ColumnInfo(name = "A_Patient_ID")
    val aPatientID: String?,

    @ColumnInfo(name = "Patient_Name")
    var aName: String?,

    @ColumnInfo(name = "Contact_Number")
    val aContact: String?,

    @ColumnInfo(name = "Date")
    val aDate: String?,

    @ColumnInfo(name = "Time")
    val aTime: String?

)