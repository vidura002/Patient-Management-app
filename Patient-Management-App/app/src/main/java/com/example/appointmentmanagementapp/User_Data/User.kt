package com.example.appointmentmanagementapp.User_Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val uid: Int?,

    @ColumnInfo(name = "Patient_ID")
    val patientID: String?,

    @ColumnInfo(name = "Name")
    var name: String?,

    @ColumnInfo(name = "Address")
    val address: String?,

    @ColumnInfo(name = "Password")
    val password: String?

)