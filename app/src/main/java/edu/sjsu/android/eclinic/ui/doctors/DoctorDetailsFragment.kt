package edu.sjsu.android.eclinic.ui.doctors

import OnMapAndViewReadyListener
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.transition.TransitionInflater
import coil.load
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.sjsu.android.eclinic.MainActivity
import edu.sjsu.android.eclinic.R
import edu.sjsu.android.eclinic.databinding.FragmentDoctorDetailsBinding
import edu.sjsu.android.eclinic.models.Appointment
import edu.sjsu.android.eclinic.utils.utils.Companion.getLocation
import edu.sjsu.android.eclinic.view_models.DoctorsViewModel
import java.util.*


class DoctorDetailsFragment : Fragment(),
    OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {
    // Main Activity
    private lateinit var mainActivity: MainActivity

    // Fragment Binding
    private lateinit var binding: FragmentDoctorDetailsBinding



    // View Model
    private val model: DoctorsViewModel by activityViewModels()

    var selected = 1
    var selectedDate: Date? = getDate(Calendar.DECEMBER, 12)

    // Firebase
    private val db = Firebase.firestore
    val appointmentCollection =
        db.collection("appointments")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(inf: LayoutInflater, ctn: ViewGroup?, state: Bundle?): View {
        binding = FragmentDoctorDetailsBinding.inflate(inf, ctn, false)
        mainActivity.setToolbar(binding.toolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.selected.observe(viewLifecycleOwner, { item ->
            binding.textName.text = item.name
            binding.textSpecialty.text = item.specialty
            binding.imagePosterDetail.load(item.poster_path)
            binding.imageToolbar.load(item.poster_path)
            binding.textAbout.text = item.overview
            binding.textPatients.text = "${item.total_patient}"
            binding.textExperience.text = "${item.experience} years"
            binding.textRating.text = "${item.rating}"
        })
        binding.collapsingToolbarDetail.isTitleEnabled = false
        binding.toolbar.title = ""
        binding.toolbarTitle.text = model.selected.value?.name
        binding.appbar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (kotlin.math.abs(verticalOffset) - appBarLayout.totalScrollRange >= -20) {
                //  Collapsed
                binding.toolbarTitle.visibility = View.VISIBLE
                binding.imageToolbar.visibility = View.VISIBLE
                binding.layoutDoctorHeader.visibility = View.INVISIBLE
            } else {
                //Expanded
                binding.toolbarTitle.visibility = View.GONE
                binding.imageToolbar.visibility = View.GONE
                binding.layoutDoctorHeader.visibility = View.VISIBLE
            }
        })

        binding.buttonCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4084601781"))
            startActivity(intent)
        }


        binding.buttonMakeAppointment.setOnClickListener {
            val dialog = Dialog(mainActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.booking_layout)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            val body = dialog.findViewById(R.id.text_appointment) as TextView
            val bookingId = dialog.findViewById(R.id.text_booking_id) as TextView
            val textDate = dialog.findViewById(R.id.text_date) as TextView

            body.text = "Make an appointment with ${model.selected.value?.name}"
            val bookingIdString = "" + ('A'..'Z').random() + (10000 until 99999).random()
            bookingId.text = bookingIdString
            textDate.text = String.format(selectedDate.toString())

            val buttonCancel = dialog.findViewById(R.id.button_cancel) as Button
            val buttonConfirm = dialog.findViewById(R.id.button_confirm) as TextView
            buttonCancel.setOnClickListener {
                dialog.dismiss()
            }
            buttonConfirm.setOnClickListener {
                dialog.dismiss()
                val doctor = model.selected.value
                val doc = appointmentCollection.document()
                val appointment = Appointment(doc.id,
                    "${doctor?.name}",
                    "${doctor?.documentId}",
                    "${doctor?.specialty}",
                    "${doctor?.poster_path}", selectedDate)
                doc.set(appointment)
                val dialogBuilder = AlertDialog.Builder(requireContext())
                dialogBuilder.setMessage("Your appointment with Dr ${model.selected.value?.name} confirmed.")
                    .setCancelable(true)
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                    })

                // create dialog box
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle("Appointment Confirmed")
                // show alert dialog
                alert.show()
            }
            dialog.show()
        }

        binding.card1.backgroundTintList =
            (ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary))
        binding.textDate1.setTextColor(Color.WHITE)
        binding.textDate11.setTextColor(Color.WHITE)
        binding.card1.setOnClickListener {
            scheduleOnClick(1)
            selected = 1
            selectedDate = getDate(Calendar.DECEMBER, 12)
        }
        binding.card2.setOnClickListener {
            scheduleOnClick(2)
            selected = 2
            selectedDate = getDate(Calendar.DECEMBER, 13)

        }
        binding.card3.setOnClickListener {
            scheduleOnClick(3)
            selected = 3
            selectedDate = getDate(Calendar.DECEMBER, 14)
        }
        binding.card4.setOnClickListener {
            scheduleOnClick(4)
            selected = 4
            selectedDate = getDate(Calendar.DECEMBER, 15)
        }

        val supportMapFragment: SupportMapFragment =
            (childFragmentManager.findFragmentById(edu.sjsu.android.eclinic.R.id.map_view) as SupportMapFragment)
        OnMapAndViewReadyListener(supportMapFragment, this)
    }

    fun getDate(month: Int, day: Int): Date? {
        val currentDate = Calendar.getInstance()
        currentDate[Calendar.MONTH] = month
        currentDate[Calendar.DAY_OF_MONTH] = day

        return currentDate.time
    }


    private fun scheduleOnClick(id: Int) {
        binding.card1.backgroundTintList =
            (ContextCompat.getColorStateList(requireContext(), R.color.white))
        binding.textDate1.setTextColor(Color.BLACK)
        binding.textDate11.setTextColor(Color.BLACK)

        binding.card2.backgroundTintList =
            (ContextCompat.getColorStateList(requireContext(), R.color.white))
        binding.textDate2.setTextColor(Color.BLACK)
        binding.textDate22.setTextColor(Color.BLACK)

        binding.card3.backgroundTintList =
            (ContextCompat.getColorStateList(requireContext(), R.color.white))
        binding.textDate3.setTextColor(Color.BLACK)
        binding.textDate33.setTextColor(Color.BLACK)

        binding.card4.backgroundTintList =
            (ContextCompat.getColorStateList(requireContext(), R.color.white))
        binding.textDate4.setTextColor(Color.BLACK)
        binding.textDate44.setTextColor(Color.BLACK)

        when (id) {
            1 -> {
                binding.card1.backgroundTintList =
                    (ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary))
                binding.textDate1.setTextColor(Color.WHITE)
                binding.textDate11.setTextColor(Color.WHITE)
            }
            2 -> {
                binding.card2.backgroundTintList =
                    (ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary))
                binding.textDate2.setTextColor(Color.WHITE)
                binding.textDate22.setTextColor(Color.WHITE)
            }
            3 -> {
                binding.card3.backgroundTintList =
                    (ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary))
                binding.textDate3.setTextColor(Color.WHITE)
                binding.textDate33.setTextColor(Color.WHITE)
            }
            4 -> {
                binding.card4.backgroundTintList =
                    (ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary))
                binding.textDate4.setTextColor(Color.WHITE)
                binding.textDate44.setTextColor(Color.WHITE)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        model.selected.value?.location?.let {
            val location = getLocation(it)
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14f))
            googleMap?.addMarker(MarkerOptions().position(location))
        }
        googleMap?.uiSettings?.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }
    }

}