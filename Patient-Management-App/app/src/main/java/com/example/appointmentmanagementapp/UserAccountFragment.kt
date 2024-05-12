package com.example.appointmentmanagementapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.appointmentmanagementapp.User_Data.User
import com.example.appointmentmanagementapp.User_Data.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserAccountFragment : Fragment() {

    private lateinit var name : TextView
    private lateinit var address: TextView
    private lateinit var idP : TextView

    private lateinit var appDB: UserDatabase

    companion object {
        fun newInstance(pID: String): UserAccountFragment {
            val fragment = UserAccountFragment()
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
        val view = inflater.inflate(R.layout.fragment_user_account, container, false)

        name = view.findViewById(R.id.textView6)
        address = view.findViewById(R.id.textView8)
        idP = view.findViewById(R.id.textView10)

        appDB = UserDatabase.getDatabase(requireActivity())

        var iid = arguments?.getString("Patient_ID")

        getSingleUserData(iid.toString())

        return view
    }

    private suspend fun setPatientData(user: User){
        withContext(Dispatchers.Main){
            name.text = user.name
            address.text = user.address
            idP.text = user.patientID
        }
    }

    private fun getSingleUserData(iid: String){

        var user: User

        GlobalScope.launch{

            user = appDB.userDao().getSinglePatient(iid)
            setPatientData(user)
        }
    }
}