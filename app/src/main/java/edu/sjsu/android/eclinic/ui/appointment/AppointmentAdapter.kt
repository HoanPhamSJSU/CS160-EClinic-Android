package edu.sjsu.android.eclinic.ui.appointment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import edu.sjsu.android.eclinic.databinding.ItemAppoinmentBinding
import edu.sjsu.android.eclinic.models.Appointment


class AppointmentAdapter : ListAdapter<Appointment, AppointmentAdapter.ViewHolder>(DIFF_CALLBACK) {

    private val appoinments: ArrayList<Appointment> = arrayListOf()

    var appointmentClick: (Appointment) -> Unit = {}

    inner class ViewHolder(binding: ItemAppoinmentBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageDoctor: ImageView = binding.imageDoctor
        val textDoctorName: TextView = binding.textDoctorName
        val textDoctorSpecialty: TextView = binding.textDoctorSpecialty
        val textAppointmentDate: TextView = binding.textAppointmentDate

        init {
            binding.root.setOnClickListener {
                Log.d("AppointmentAdapter", "------- Click")
                appointmentClick.invoke(appoinments[adapterPosition])
            }
        }
    }

    fun addAppointments(newDoctors: List<Appointment>) {
        appoinments.addAll(newDoctors)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAppoinmentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AppointmentAdapter.ViewHolder, position: Int) {
        val appointment: Appointment = appoinments[position]
        holder.textDoctorName.text = appointment.doctor_name
        holder.textDoctorSpecialty.text = appointment.doctor_specialty
        holder.textAppointmentDate.text = String.format("%s ", appointment.date)
        holder.imageDoctor.load(appointment.doctor_image)
    }

    override fun getItemCount(): Int {
        return appoinments.size
    }


    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Appointment> =
            object : DiffUtil.ItemCallback<Appointment>() {
                override fun areItemsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
                    return oldItem.documentId == newItem.documentId
                }

                override fun areContentsTheSame(
                    oldItem: Appointment,
                    newItem: Appointment,
                ): Boolean {
                    return oldItem.doctor_name == newItem.doctor_name && oldItem.doctor_specialty == newItem.doctor_specialty
                }
            }
    }
}