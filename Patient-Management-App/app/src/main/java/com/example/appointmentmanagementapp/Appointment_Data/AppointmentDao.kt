package com.example.appointmentmanagementapp.Appointment_Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppointmentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(appointment: Appointment)

    @Query("SELECT * FROM Appointment WHERE A_Patient_ID = :patientID")
    fun getAllAppointments(patientID: String): List<Appointment>

    @Query("SELECT * FROM Appointment WHERE uid = :uid")
    fun getSingleAppointment(uid: String): Appointment

    @Query("UPDATE Appointment SET Patient_Name =:patient_Name, Contact_Number =:contact_Number, Date =:date, Time =:time WHERE uid = :uid")
    fun updateAppointment(uid: String, patient_Name: String, contact_Number:String, date:String, time: String)

    @Query("DELETE FROM Appointment WHERE uid = :uid")
    fun deleteAppointment(uid: String)
}