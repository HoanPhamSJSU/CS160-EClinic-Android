package edu.sjsu.android.eclinic.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.sjsu.android.eclinic.MainActivity
import edu.sjsu.android.eclinic.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    // TAG
    private val TAG = "Home Fragment"

    // Main Activity
    private lateinit var mainActivity: MainActivity

    // Fragment Binding
    private lateinit var binding: FragmentLoginBinding

    // Firebase
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        mainActivity.finish()
    }

}