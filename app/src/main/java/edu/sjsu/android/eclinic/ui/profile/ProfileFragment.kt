package edu.sjsu.android.eclinic.ui.profile

import OnMapAndViewReadyListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.sjsu.android.eclinic.MainActivity
import edu.sjsu.android.eclinic.databinding.FragmentProfileBinding
import edu.sjsu.android.eclinic.models.Profile
import edu.sjsu.android.eclinic.utils.EclinicNavigation
import edu.sjsu.android.eclinic.utils.utils.Companion.getLocation
import kotlin.math.abs

class ProfileFragment : Fragment(), OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {
    // TAG
    private val TAG = "Home Fragment"

    // Main Activity
    private lateinit var mainActivity: MainActivity

    // Fragment Binding
    private lateinit var binding: FragmentProfileBinding

    //Map
    var googleMap: GoogleMap? = null

    // Firebase
    private val db = Firebase.firestore
    private val profileCollection = db.collection("profiles")

    private var profile: Profile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(inf: LayoutInflater, ctn: ViewGroup?, state: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inf, ctn, false)
        mainActivity.setToolbar(binding.toolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadProfile("P5otipkw0STIuLRNQSUceVzMoNr2")
        binding.buttonMakeAppointment.setOnClickListener { EclinicNavigation.toAppointments(this) }
        binding.buttonSignout.setOnClickListener { EclinicNavigation.toLogin(this) }
        binding.collapsingToolbarDetail.isTitleEnabled = false
        binding.toolbar.title = ""
        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange >= -20) {
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
        val supportMapFragment: SupportMapFragment =
            (childFragmentManager.findFragmentById(edu.sjsu.android.eclinic.R.id.map_view) as SupportMapFragment)
        OnMapAndViewReadyListener(supportMapFragment, this)
    }

    private fun loadProfile(profileID: String) {
        profileCollection.document(profileID).get().addOnSuccessListener { profileSnapshot ->
            profile = profileSnapshot.toObject(Profile::class.java)
            binding.toolbarTitle.text = profile?.name
            binding.textName.text = profile?.name
            binding.textSpecialty.text = profile?.specialty
            binding.imagePosterDetail.load(profile?.profile_image)
            binding.imageToolbar.load(profile?.profile_image)
            binding.textAbout.text = profile?.overview
            binding.textEmail.text = profile?.email
            binding.textUsername.text = profile?.username
            binding.textPhone.text = profile?.phone
            profile?.location?.let {
                val location = getLocation(it)
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14f))
                googleMap?.addMarker(MarkerOptions().position(location))
            }
        }.addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents: ", exception)
        }
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        profile?.location?.let {
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
