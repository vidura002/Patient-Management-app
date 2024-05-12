package com.example.appointmentmanagementapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.appointmentmanagementapp.User_Data.User
import com.example.appointmentmanagementapp.User_Data.UserDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.log

class HomeScreen : AppCompatActivity() {

    private lateinit var logOut: ImageView

    private lateinit var appDB: UserDatabase

    private lateinit var pID: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        pID = findViewById(R.id.textView13)
        pID.text = intent.getStringExtra("Patient_ID")

        val pId = intent.getStringExtra("Patient_ID")
        println(pId)

        logOut = findViewById(R.id.imageView3)

        appDB = UserDatabase.getDatabase(this)

        var viewPager = findViewById<ViewPager>(R.id.viewPager)
        var tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        val fragmentAdapter = FragmentAdapter(supportFragmentManager, pId.toString())
        fragmentAdapter.addFragment(AppointmentFragment.newInstance(pId.toString()),"Appointments")
        fragmentAdapter.addFragment(UserAccountFragment.newInstance(pId.toString()),"My Account")

        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)


        logOut.setOnClickListener{
            Toast.makeText(applicationContext, "LogOut Success!", Toast.LENGTH_SHORT).show()
            intent = Intent(applicationContext, LoginScreen::class.java)
            startActivity(intent)
            finish()
        }
    }


}