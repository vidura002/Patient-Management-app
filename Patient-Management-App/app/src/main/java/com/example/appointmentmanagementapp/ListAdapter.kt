package com.example.appointmentmanagementapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.appointmentmanagementapp.Appointment_Data.Appointment
import com.example.appointmentmanagementapp.Appointment_Data.AppointmentDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListAdapter(
    private var appointmentList: List<Appointment>,
    private val context: Context,
    private var appointmentDB: AppointmentDatabase,
) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val patientName : TextView = itemView.findViewById(R.id.textView15)
        val time : TextView = itemView.findViewById(R.id.textView14)
        val date : TextView = itemView.findViewById(R.id.textView13)
        val edit: ImageView = itemView.findViewById(R.id.imageView6)
        val tile: RelativeLayout = itemView.findViewById(R.id.appointmentListTile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_appointment_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = appointmentList[position]
        holder.patientName.text = currentItem.aName
        holder.time.text = currentItem.aTime
        holder.date.text = currentItem.aDate

        holder.edit.setOnClickListener{
            val intent = Intent(context, EditAppointmentScreen::class.java)
            intent.putExtra("uid",currentItem.uid.toString())
            context.startActivity(intent)
        }

        holder.tile.setOnClickListener{
            val intent = Intent(context, ViewAppointmentScreen::class.java)
            intent.putExtra("uid",currentItem.uid.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return appointmentList.size
    }

}