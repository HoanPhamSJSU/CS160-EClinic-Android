package edu.sjsu.android.eclinic.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.sjsu.android.eclinic.MainActivity
import edu.sjsu.android.eclinic.R
import edu.sjsu.android.eclinic.databinding.FragmentHomeBinding
import edu.sjsu.android.eclinic.databinding.ItemTopDoctorBinding
import edu.sjsu.android.eclinic.models.Doctor
import edu.sjsu.android.eclinic.models.Profile
import edu.sjsu.android.eclinic.utils.EclinicNavigation.Companion.toInfo
import edu.sjsu.android.eclinic.utils.EclinicNavigation.Companion.toProfile
import edu.sjsu.android.eclinic.view_models.DoctorsViewModel

class HomeFragment : Fragment() {
    // TAG
    private val TAG = "Home Fragment"

    // Main Activity
    private lateinit var mainActivity: MainActivity

    // Fragment Binding
    private lateinit var binding: FragmentHomeBinding

    // View Model
    private val model: DoctorsViewModel by activityViewModels()

    // Adapter
    private lateinit var topDoctorsAdapter: TopDoctorsAdapter

    // Firebase
    val db = Firebase.firestore
    val doctorCollection = db.collection("doctors")
    val profileCollection = db.collection("profiles")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(inf: LayoutInflater, ctn: ViewGroup?, state: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inf, ctn, false)
        mainActivity.setToolbar(binding.toolbar)
        topDoctorsAdapter = TopDoctorsAdapter()
        binding.rcTop.adapter = topDoctorsAdapter
        binding.rcTop.setHasFixedSize(true)
        topDoctorsAdapter.onDoctorItemClick = { view, doctor ->
            toDetail(view, doctor)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "--- onViewCreated ---")
        binding.buttonCovid.setOnClickListener { buttonCovidClick() }
        binding.buttonHospital.setOnClickListener { buttonHospitalClick() }
        binding.buttonEmergency.setOnClickListener { buttonEmergencyClick() }
        binding.buttonPills.setOnClickListener { buttonPillsClick() }
        binding.layoutProfile.setOnClickListener { toProfile(this) }
        loadProfile("P5otipkw0STIuLRNQSUceVzMoNr2")
        loadDoctors()
    }

    private fun loadDoctors() {
        doctorCollection.get().addOnSuccessListener { documents ->
            val doctors = arrayListOf<Doctor>()
            for (document in documents) {
                val doctor: Doctor = document.toObject(Doctor::class.java)
                doctors.add(doctor)
                doctor.documentId = document.id
                Log.d(TAG, "${doctor.documentId} => ${doctor.name}")
            }
            topDoctorsAdapter.addDoctors(doctors)
        }.addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents: ", exception)
        }
    }


    private fun buttonCovidClick() {
        model.setContent("Symptoms may appear 2-14 days after exposure to the virus. People with these symptoms may have COVID-19:\n" +
                "Fever or chills\n" +
                "Cough\n" +
                "Shortness of breath or difficulty breathing\n" +
                "Fatigue\n" +
                "Muscle or body aches\n" +
                "Headache\n" +
                "New loss of taste or smell\n" +
                "Sore throat\n" +
                "Congestion or runny nose\n" +
                "Nausea or vomiting\n" +
                "Diarrhea\n" +
                "Look for emergency warning signs for COVID-19. If someone is showing any of these signs, seek emergency medical care immediately:\n" +
                "Trouble breathing\n" +
                "Persistent pain or pressure in the chest\n" +
                "New confusion\n" +
                "Inability to wake or stay awake\n" +
                "Pale, gray, or blue-colored skin, lips, or nail beds, depending on skin tone")
        model.setTitle("Covid 19 - Information")
        toInfo(this)
    }


    private fun buttonHospitalClick() {
        model.setContent("Adventist Health - Roseville, California\n" +
                "Alameda Hospital - Alameda, California\n" +
                "Alvarado Hospital Medical Center - San Diego, California\n" +
                "Antelope Valley Hospital Medical Center - Lancaster, California\n" +
                "Arrowhead Regional Medical Center - Colton, California\n" +
                "Bakersfield Heart Hospital - Bakersfield, California\n" +
                "Barlow Respiratory Hospital - Los Angeles, California\n" +
                "Barstow Community Hospital - Barstow, California\n" +
                "Beverly Hospital - Montebello, California\n" +
                "Bridge to Wellness (Adult Psychiatric Partial Hospitalization) - San Francisco, California\n" +
                "Brotman Medical Center - Culver City, California\n" +
                "California Pacific Medical Center - San Francisco, California\n" +
                "Cedars-Sinai Medical Center - Los Angeles, California\n" +
                "Centinela Hospital Medical Center - Inglewood, California\n" +
                "Century City Hospital - Los Angeles, California\n" +
                "Chapman Medical Center - Orange California\n" +
                "Children's Hospital and Health Center - San Diego, California\n" +
                "Childrens Hospital Los Angeles - Los Angeles, California\n" +
                "Children's Hospital of Orange County - Orange County, California\n" +
                "Chino Valley Medical Center - Chino, California\n" +
                "Citrus Valley Health Partners (Citrus Valley Medical Center,Inter-Community Campus & Queen of the Valley Campus, Foothill Presbyterian Hospital, Citrus Valley Hospice) - West Covina, Covina, California\n" +
                "City of Hope (National Medical Center and Beckman Research Institute) - Duarte, California\n" +
                "Coastal Communities Hospital - Santa Ana, California\n" +
                "Community Hospital of the Monterey Penisula - Monterey, California\n" +
                "Community Hospital of Huntington Park - Huntington Park, California\n" +
                "Community Hospital of Long Beach - Long Beach, California\n" +
                "Community Hospital of Los Gatos - Los Gatos, California\n" +
                "Community Memorial Hospital - Ventura, California\n" +
                "Community Medical Centers - Fresno, California\n" +
                "Cottage Hospital - Santa Barbara, California\n" +
                "Daniel Freeman Marina Hospital - Marina del Rey, California\n" +
                "Delta Memorial - Contra Costa, California\n" +
                "Desert Regional Medical Center - Palm Springs, California\n" +
                "Desert Valley Hospital - Victorville, California\n" +
                "Doctors Hospital of Manteca - Manteca, California\n" +
                "Doctors Medical Center - Pinole Campus - Pinole, California\n" +
                "Doctors Medical Center - San Pablo Campus - San Pablo, California\n" +
                "Doctors Medical Center of Modesto - Modesto, California\n" +
                "Dominican Hospital - Santa Cruz, California\n" +
                "East Valley Hospital - Glendora, California\n" +
                "Eden Medical Center - Castro Valley, California\n" +
                "El Camino Hospital - Mountain View, California\n" +
                "Eisenhower Medical Center - Rancho Mirage, California\n" +
                "Emanuel Medical Center - Turlock, California\n" +
                "Encino-Tarzana Regional Medical Center - Encino and Tarzana, California\n" +
                "Enlowe General Hospital - Chico Califorina, California\n" +
                "Fountain Valley Regional Hospital - Fountain Valley, California\n" +
                "Frazier Mountain Community Health Frazier Park, California\n" +
                "Freemont Medical Center - Freemont, California\n" +
                "Fremont Hospital - Yubacity, California\n" +
                "Fremont Hospital - Fremont, California\n" +
                "Fresno Community Hospital - Fresno, California\n" +
                "Fresno Heart Hospital - Fresno, California\n" +
                "Garden Grove Hospital and Medical Center - Garden Grove, California\n" +
                "Garfield Medical Center - Monterey Park, California\n" +
                "Glendale Memorial Hospital and Health Center - Glendale, California\n" +
                "Good Samaritan Hospital - San Jose, California\n" +
                "Good Samaritan Hospital - Los Angeles, California\n" +
                "Greater El Monte Community Hospital - South El Monte, California\n" +
                "Henry Mayo Newhall Memorial Hospital - Valencia, California\n" +
                "Hoag Memorial Hospital Presbyterian - Newport Beach, California\n" +
                "Hospital Council of Northern and Central California - California\n" +
                "Huntington Memorial Hospital - Pasadena, California\n" +
                "Irvine Medical Center - Irvine, California\n" +
                "John Douglas French Center - Los Alamitos, California\n" +
                "John Muir Medical Center - Walnut Creek, California\n" +
                "John F. Kennedy Memorial Hospital - Indio, California\n" +
                "Kaiser Permanente - California\n" +
                "Kaweah Delta District Hospital - Visalia, California\n" +
                "Lakewood Regional Medical Center - Lakewood, California\n" +
                "Las Encias Hospital - Pasadena, California\n" +
                "Little Company of Mary Hospital - torrance, California\n" +
                "Los Alamitos Medical Center - Los Alamitos, California\n" +
                "Los Angeles County Harbor-UCLA Medical Center - Torrance, California\n" +
                "Loma Linda University Medical Center - Loma Linda, California\n" +
                "Los Robles Hospital - Thousand Oaks, California\n" +
                "Lucile Salter Packard Children's Hospital at Stanford - Palo Alto, CA\n" +
                "Mad River Community Hospital - Arcata, California\n" +
                "Parkview Community Hospital - Riverside, California\n" +
                "Parkview Hospital - Riverside, California\n" +
                "Placentia Linda Hospital - Placentia, California\n" +
                "Providence Holy Cross Hospital - Sylmar, California\n" +
                "Queen of Angels-Hollywood Presbyterian Medical Center - Los Angeles, California\n" +
                "Queen of the Valley - West Covina, California\n" +
                "Queen of the Valley - Napa, California\n" +
                "Rancho Springs Medical Center - Murrieta, California\n" +
                "Redding Medical Center - Redding, California\n" +
                "Regional Medical Center of San Jose - San Jose, California\n" +
                "Riverside Community Hospital - Riverside, California\n" +
                "Riverside County Regional Medical Center - Riverside, California\n" +
                "Saddleback Memorial Hospital - Laguna Hills, California\n" +
                "San Miguel Hospital - Contra Costa, California\n" +
                "Saint Agnes Medical Center - Fresno, California\n" +
                "Saint Francis Medical Center - Santa Barbara, California\n" +
                "Saint Francis Memorial Hospital (Spine Center) - San Francisco, California\n" +
                "Saint John's Health Center - Santa Monica, California\n" +
                "Saint Joseph - Glendale, California\n" +
                "San Dimas Community Hospital - San Dimas, California\n" +
                "San Francisco General Hospital Medical Center - San Francisco,California\n" +
                "San Joaquin Community Hospital - Bakersfield, California\n" +
                "Scrippshealth - San Diego, California\n" +
                "Sierra Nevada Memorial Hospita - Grass Valley, California\n" +
                "St. Bernadines Medical Center - San Bernadino, California\n" +
                "St. Jude Medical Center - Fullerton, California\n" +
                "St. Mary's Medical Center - San Francisco, California\n" +
                "Saint Rose Hospital - Hayward, California\n" +
                "Torrance Medical Center - Torrance, California\n" +
                "Tri-City Medical Center - Oceanside, California\n" +
                "Tuolumne General Hospital - Sonora, California\n" +
                "Twin Cities Community Hospital - Templeton, California\n" +
                "University of California San Francisco Medical Center - San Francisco, California\n" +
                "UCSF/Mount Zion Medical Center - San Francisco, California\n" +
                "UCSF Medical Center - San Francisco, California\n" +
                "University of California Davis Medical Center - California\n" +
                "UCSD Medical Center - San Diego, California\n" +
                "USC (University of Southern California) University Hospital - Los Angeles, California\n" +
                "VA Medical Center - San Francisco, California\n" +
                "Valley Children's Hospital - Madera, California\n" +
                "ValleyCare Health System - Livermore, California\n" +
                "Valley Presbytarian - Van Nuys, California\n" +
                "Ventura Community Hospital - Ventura, California\n" +
                "Victor Valley Hospital - VictorVille, California\n" +
                "Western Medical Center - Santa Ana, California\n" +
                "West Hills Hospital - West Hills, California\n" +
                "West Los Angeles Memorial Hospital - Los Angles, California\n" +
                "Western Medical Center - Anaheim - Anaheim, California\n" +
                "White Memorial Medical Center - L.A. California\n" +
                "Whittier Hospital Medical Center - Whittier, California")
        model.setTitle("Hospital in California")
        toInfo(this)
    }

    private fun buttonEmergencyClick() {
        val content = "Report COVID-19 Concerns and Access Services\n" +
                "To report a concern about a business, school, or healthcare provider or to find a COVID-19 service, go to www.sccCOVIDconcerns.org\u200B.\u200B\n" +
                "\n" +
                "COVID-19 Information and Services Available from 211\n" +
                "Call 2-1-1 to speak with an operator about programs and services to assist families in Santa Clara County.\u200B\n" +
                "\n" +
                "COVID-19 Business Call Center \n" +
                "For any inquires related to your business or workplace operating under the current COVID-19 regulation in Santa Clara County\u200B\u200B\u200B\u200B\u200B, call (408) 961-5500, Monday - Friday, 8am-5pm.\u200B\n" +
                "\n" +
                "Prefer To Message Us about COVID-19?\n" +
                "Contact the County about COVID-19, \u200Bincluding questions about the Risk Reduction Order, Mandatory Industry Directives, and more.\n" +
                "\n" +
                "Isolation & Quarantine Support Program\n" +
                "For support services in order to safely isolate or quarantine (including housing, transportation, meals, or other supports), call 408-808-7770 or visit Isolation and Quarantine Support Program\n" +
                "\n" +
                "SCU Lightning Complex Fires Hotline\n" +
                "For information for evacuees due to the SCU Lightning Complex Fires, call 408-808-7778\n" +
                "\n" +
                "Santa Clara County CAN Hotline\n" +
                "For COVID-19 Assistance Navigation for applying for unemployment insurance, disability, paid family lean and other safety net programs, call 408-809-2124 or email at scc-can-info@wpusa.org\n" +
                "\n" +
                "Second Harvest Food Bank\n" +
                "For food assistance, call 1-800-984-3663\n" +
                "\n" +
                "County's Joint Operations Center\n" +
                "For housing support, call 408-278-6420.\u200B\n" +
                "\n" +
                "County of Santa Clara Office of Labor Standards Enforcement\n" +
                "If your employer is not complying with business requirements to operate or the Social Distancing Protocol, call 1-866-870-7725\n" +
                "\n" +
                "County’s Patient Access Department\n" +
                "If you need health insurance, call 408-885-CARE\n" +
                "\n" +
                "COVID-19 testing in the County\n" +
                "Visit the County Public Health Department website: www.sccfreetest.org.\n" +
                "\n" +
                "To see if you qualify for a program that can cover a portion of your lost wages due to COVID-19 visit: sccfairworkplace.org or call 1-866-870-7725."
        model.setContent("Emergency List")
        model.setContent(content)
        toInfo(this)

    }

    private fun buttonPillsClick() {
        model.setTitle("Medicines - Pills")
        val content =
            "https://www.cdc.gov/\n\nhttps://www.nih.gov/health-information \n\nhttps://www.pfizer.com/\n "
        model.setContent(content)
        toInfo(this)
    }

    private fun largeLog(tag: String?, content: String) {
        if (content.length > 4000) {
            Log.d(tag, content.substring(0, 4000))
            largeLog(tag, content.substring(4000))
        } else {
            Log.d(tag, content)
        }
    }

    private fun loadProfile(profileID: String) {
        var stringName = "Hoan 👋"
        binding.textName.text = stringName
        binding.profileImage.load("https://raw.githubusercontent.com/hung-p/ClinicImages/main/images/profiles/hoan.png")

        profileCollection.document(profileID).get().addOnSuccessListener { profileSnapshot ->
            val profile = profileSnapshot.toObject(Profile::class.java)
            stringName = "${profile?.name} 👋"
            binding.textName.text = stringName
            binding.profileImage.load(profile?.profile_image)

        }.addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents: ", exception)
        }
    }

    private fun toDetail(view: ItemTopDoctorBinding, doctor: Doctor) {
        model.select(doctor)
        Log.d("Doctor", "Doctor: $doctor")
        view.cardPoster.transitionName = "home_doctor_poster"
        view.textName.transitionName = "home_doctor_name"
        view.textSpecialty.transitionName = "home_doctor_specialty"
        val extras = FragmentNavigatorExtras(
            view.cardPoster to "transition_doctor_details_poster",
            view.textName to "transition_doctor_details_name",
            view.textSpecialty to "transition_doctor_details_specialty")
        findNavController().navigate(R.id.action_nav_home_to_nav_doctor_details, null, null, extras)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray("ARTICLE_SCROLL_POSITION",
            intArrayOf(binding.homeNestedScroll.scrollX, binding.homeNestedScroll.scrollY))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val position = savedInstanceState?.getIntArray("ARTICLE_SCROLL_POSITION")
        if (position != null) binding.homeNestedScroll.post(Runnable {
            binding.homeNestedScroll.scrollTo(position[0],
                position[1])
        })
    }


    //    private fun generateDoctors(): ArrayList<Doctor> {
//        val doctorList = arrayListOf<Doctor>()
//        doctorList.add(Doctor("2WbygxKViAXBAZ2EqJUh",1,
//            "Dr. Malcolm John",
//            "Infectious Diseases",
//            "",
//            "https://raw.githubusercontent.com/hung-p/ClinicImages/main/images/doctors/doctor1_512.jpg",
//            "Dr. Malcolm John, an expert in infectious diseases, is director of the UCSF HIV/AIDS Program, one of the most comprehensive HIV and AIDS treatment programs in the country. In addition to his work at UCSF, John is an HIV consultant for Larkin Street Youth Services in San Francisco.\n" +
//                    "John's research addresses molecular and immunologic components of diseases associated with the human papillomavirus (HPV) in HIV-infected patients, particularly among African Americans. He also studies differences in HIV care and outcomes among African Americans.\n" +
//                    "John earned his medical degree at Harvard University and a master's degree in public health at the University of California, Berkeley. He completed a residency and fellowship in internal medicine at Massachusetts General Hospital. He completed a fellowship in infectious diseases with the UCSF Center for AIDS Prevention Studies.\n" +
//                    "John has received numerous honors, including an award from the Robert Wood Johnson Foundation's Harold Amos Medical Faculty Development Program.",
//            "37.3282354,-121.9559742",
//            79,
//            4.8f, 5000, 15))
//        doctorList.add(Doctor("892geKANe73XdYLl2Dqt",2,
//            "Dr. Hannah Holland",
//            "General Surgery",
//            "",
//            "https://raw.githubusercontent.com/hung-p/ClinicImages/main/images/doctors/doctor2_512.jpg",
//            "Dr. Hannah Holland is a General Surgery Specialist in Los Gatos, CA.  She is affiliated with medical facilities Good Samaritan Hospital and El Camino Health - Los Gatos Campus.  She is accepting new patients.",
//            "37.3735315,-121.9392914",
//            58,
//            4.9f, 2500, 10))
//        doctorList.add(Doctor("NEi12EmETC3EEaErQjhX",3,
//            "Dr. Terry Wahls",
//            "Neurologists",
//            "",
//            "https://raw.githubusercontent.com/hung-p/ClinicImages/main/images/doctors/doctor3_512.jpg",
//            " is one of few select physicians in the country that are triple Board certified by American Board of Psychiatry and Neurology(ABPN), American Board of Internal Medicine(ABIM) and Interventional Pain Management with certification in Addiction medicine. He completed his fellowship in interventional pain management at UC Davis Medical Center - Department of Anesthesiology",
//            "37.4803832,-121.8561037",
//            33,
//            4.8f, 2800, 8))
//        doctorList.add(Doctor("gNsNZBkD6It8eCDEvDav",4,
//            "Dr. Samantha Jennings",
//            "Pediatrician",
//            "",
//            "https://raw.githubusercontent.com/hung-p/ClinicImages/main/images/doctors/doctor4_512.jpg",
//            "Dr. Samantha Jennings is a well respected and trusted pediatrician located in between Los Gatos and San Jose, Ca.",
//            "37.5080721,-122.3456057",
//            28,
//            4.7f, 1200, 5))
//        doctorList.add(Doctor("kfe3tLL93eVLOGvgVVlw",5,
//            "Dr. Randal Pham",
//            "Surgery",
//            "",
//            "https://raw.githubusercontent.com/hung-p/ClinicImages/main/images/doctors/doctor5_512.jpg",
//            "Randal Pham, MD, MS, FACS, is an award-winning, board-certified eye surgeon in San Jose, California. He has a special interest in the prevention and treatment of world blindness.\n" + "Dr. Pham earned his undergraduate degree with honors at the University of California in Berkeley. He completed his medical degree at the University of California San Francisco School of Medicine and then an ophthalmology residency training at the Case Western Reserve University MetroHealth St. Luke’s Medical Center in Cleveland, Ohio. Dr. Pham went on to finish an oculofacial plastic surgery fellowship with surgeons Sterling S. Baker, MD, and P.",
//            "37.46445,-122.175339",
//            18,
//            4.8f, 1800, 7))
//        doctorList.add(Doctor("y5NPKIqZaAl08QmF2BjD",6,
//            "Dr. Jose Hernandez",
//            "Cardiologists",
//            "",
//            "https://raw.githubusercontent.com/hung-p/ClinicImages/main/images/doctors/doctor6_512.jpg",
//            "Dr. Jose Hernandez, MD is a Internal Medicine Specialist in Burbank, CA and has over 12 years of experience in the medical field.  He graduated from UNIV OF IL COLL OF MED medical school in 2009.  He is affiliated with medical facilities Casa Colina Hospital and Providence Saint Joseph Medical Center.  Be sure to call ahead with Dr. Hernandez to book an appointment.",
//            "37.1854342,-121.8948604",
//            38,
//            4.7f, 2200, 9))
//        return doctorList;
//    }
}