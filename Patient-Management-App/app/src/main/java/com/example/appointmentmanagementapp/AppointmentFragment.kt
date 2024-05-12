package com.example.appointmentmanagementapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appointmentmanagementapp.Appointment_Data.Appointment
import com.example.appointmentmanagementapp.Appointment_Data.AppointmentDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppointmentFragment : Fragment() {

    private lateinit var floatingBtnAdd: FloatingActionButton

    private lateinit var recyclerView: RecyclerView

    private lateinit var appointmentDB: AppointmentDatabase

    private lateinit var appointmentsList: ArrayList<Appointment>

    private lateinit var adapter: ListAdapter

    private lateinit var patientId : String

    companion object {
        fun newInstance(pID: String): AppointmentFragment {
            val fragment = AppointmentFragment()
            val args = Bundle()
            args.putString("Patient_ID", pID)
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_appointment, container, false)

        println(arguments?.getString("Patient_ID"))
        floatingBtnAdd = view.findViewById(R.id.floatingBtn)

        appointmentsList = arrayListOf()

        appointmentDB = AppointmentDatabase.getDatabase(requireContext())

        recyclerView = view.findViewById(R.id.appList) // Make sure the ID matches your XML layout
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ListAdapter(appointmentsList, requireContext(),appointmentDB)
        recyclerView.adapter = adapter

        patientId = arguments?.getString("Patient_ID").toString()
        if (patientId != null) {
            loadAppointmentsFromDatabase(patientId)
        }

        //arguments?.getString("Patient_ID")?.let { loadAppointmentsFromDatabase(it) }


        floatingBtnAdd.setOnClickListener{
            val intent = Intent(requireContext(), AddAppointmentScreen::class.java)
            intent.putExtra("Patient_ID", arguments?.getString("Patient_ID"))
            startActivity(intent)
        }

        return view

    }

    override fun onResume() {
        super.onResume()
        loadAppointmentsFromDatabase(patientId)
        println(patientId)
    }

    private fun loadAppointmentsFromDatabase(pid: String) {
        GlobalScope.launch(Dispatchers.IO) {
            // Fetch appointments from the database
            val appointments = appointmentDB.appointmentDao().getAllAppointments(pid)

            // Update the RecyclerView on the UI thread
            launch(Dispatchers.Main) {
                appointmentsList.clear()
                appointmentsList.addAll(appointments)
                adapter.notifyDataSetChanged()
            }
        }
    }

}