package com.example.appointmentmanagementapp.User_Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(user: User)

    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM User WHERE Patient_ID = :patientID AND Password = :password) THEN 1 ELSE 0 END")
     fun isPasswordCorrect(patientID: String, password: String): Int

     @Query("SELECT * FROM User WHERE Patient_ID = :patientID")
     fun getSinglePatient(patientID: String): User

    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM User WHERE Patient_ID = :patientID) THEN 1 ELSE 0 END")
    fun isPatientIDValid(patientID: String): Int

}