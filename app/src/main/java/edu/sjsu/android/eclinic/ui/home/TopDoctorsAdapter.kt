package edu.sjsu.android.eclinic.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import edu.sjsu.android.eclinic.databinding.ItemTopDoctorBinding
import edu.sjsu.android.eclinic.models.Doctor

class TopDoctorsAdapter : ListAdapter<Doctor, TopDoctorsAdapter.ViewHolder>(DIFF_CALLBACK) {

    private val doctors: ArrayList<Doctor> = arrayListOf()

    var onDoctorItemClick: (ItemTopDoctorBinding, Doctor) -> Unit = { _, _ -> }

    inner class ViewHolder(binding: ItemTopDoctorBinding) : RecyclerView.ViewHolder(binding.root) {
        val imagePoster: ImageView = binding.imagePoster
        val textName: TextView = binding.textName
        val textReviewCount: TextView = binding.textReviewCount
        val textReviewAverage: TextView = binding.textReviewAverage

        init {
            binding.root.setOnClickListener {
                onDoctorItemClick.invoke(binding, doctors[adapterPosition])
            }
        }
    }

    fun addDoctor(newDoctor: Doctor) {
        doctors.add(newDoctor)
        submitList(doctors)
    }

    fun addDoctors(newDoctors: List<Doctor>) {
        doctors.addAll(newDoctors)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTopDoctorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TopDoctorsAdapter.ViewHolder, position: Int) {
        val doctor: Doctor = doctors[position]
        holder.textName.text = doctor.name
        holder.textReviewCount.text = String.format("%d Reviews", doctor.review_count)
        holder.textReviewAverage.text = String.format("%.1f ", doctor.rating)

        holder.imagePoster.load(doctor.poster_path) {
            crossfade(true)
        }
    }

    override fun getItemCount(): Int {
        return doctors.size
    }


    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Doctor> =
            object : DiffUtil.ItemCallback<Doctor>() {
                override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
                    return oldItem.documentId == newItem.documentId
                }
                override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
                    return oldItem.name == newItem.name && oldItem.specialty == newItem.specialty
                }
            }
    }
}