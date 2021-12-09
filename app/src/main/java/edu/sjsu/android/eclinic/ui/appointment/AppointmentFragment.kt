package edu.sjsu.android.eclinic.ui.appointment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.sjsu.android.eclinic.MainActivity
import edu.sjsu.android.eclinic.databinding.FragmentAppointmentBinding
import edu.sjsu.android.eclinic.models.Appointment
import edu.sjsu.android.eclinic.view_models.DoctorsViewModel

class AppointmentFragment : Fragment() {
    // TAG
    private val TAG = "Home Fragment"

    // Main Activity
    private lateinit var mainActivity: MainActivity

    // Fragment Binding
    private lateinit var binding: FragmentAppointmentBinding

    // Adapter
    private lateinit var appointmentAdapter: AppointmentAdapter

    // Firebase
    private val db = Firebase.firestore
    val appointmentCollection =
        db.collection("appointments").orderBy("date", Query.Direction.ASCENDING)
    val doctorCollection = db.collection("doctors")

    // View Model
    private val model: DoctorsViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(inf: LayoutInflater, ctn: ViewGroup?, state: Bundle?): View {
        binding = FragmentAppointmentBinding.inflate(inf, ctn, false)
        mainActivity.setToolbar(binding.toolbar)
        appointmentAdapter = AppointmentAdapter()
        binding.rcTop.adapter = appointmentAdapter
        binding.rcTop.setHasFixedSize(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAppointment()
        appointmentAdapter.appointmentClick = { appointment ->
            Log.d("Appointment", "---> itemClick")
//            doctorCollection.document(appointment.doctor_id).get().addOnSuccessListener {
//
//                val doctor: Doctor? = it.toObject(Doctor::class.java)
//                doctor?.let {
//                    model.select(doctor)
//                    view.imageDoctor.transitionName = "appointment_doctor_image"
//                    view.textDoctorName.transitionName = "appointment_doctor_name"
//                    view.textDoctorSpecialty.transitionName = "appointment_doctor_specialty"
//                    val extras = FragmentNavigatorExtras(
//                        view.imageDoctor to "transition_doctor_details_poster",
//                        view.textDoctorName to "transition_doctor_details_name",
//                        view.textDoctorSpecialty to "transition_doctor_details_specialty")
//                    EclinicNavigation.toDoctorDetail(this, extras)
//                }
//            }
        }
    }

    private fun loadAppointment() {
        appointmentCollection.get().addOnSuccessListener { documents ->
            val appointments = arrayListOf<Appointment>()
            for (document in documents) {
                val doctor: Appointment = document.toObject(Appointment::class.java)
                appointments.add(doctor)
                doctor.documentId = document.id
                Log.d(TAG, "${doctor.documentId} => ${doctor.doctor_name}")
            }
            appointmentAdapter.addAppointments(appointments)
        }.addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents: ", exception)
        }
    }
}