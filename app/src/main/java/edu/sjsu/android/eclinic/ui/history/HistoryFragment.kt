package edu.sjsu.android.eclinic.ui.history

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
import edu.sjsu.android.eclinic.databinding.FragmentHistoryBinding
import edu.sjsu.android.eclinic.models.History
import edu.sjsu.android.eclinic.view_models.DoctorsViewModel

class HistoryFragment : Fragment() {
    // TAG
    private val TAG = "Home Fragment"

    // Main Activity
    private lateinit var mainActivity: MainActivity

    // Fragment Binding
    private lateinit var binding: FragmentHistoryBinding

    // Adapter
    private lateinit var historyAdapter: HistoryAdapter

    // Firebase
    private val db = Firebase.firestore
    val historyCollection =
        db.collection("history").orderBy("date", Query.Direction.ASCENDING)

    // View Model
    private val model: DoctorsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(inf: LayoutInflater, ctn: ViewGroup?, state: Bundle?): View {
        binding = FragmentHistoryBinding.inflate(inf, ctn, false)
        mainActivity.setToolbar(binding.toolbar)
        historyAdapter = HistoryAdapter()
        binding.rcTop.adapter = historyAdapter
        binding.rcTop.setHasFixedSize(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadHistory()
        historyAdapter.itemClick = { history ->
            Log.d("Appointment", "---> itemClick")
        }
    }

    private fun loadHistory() {
        historyCollection.get().addOnSuccessListener { documents ->
            val appointments = arrayListOf<History>()
            for (document in documents) {
                val his: History = document.toObject(History::class.java)
                appointments.add(his)
                his.documentId = document.id
                Log.d(TAG, "${his.documentId} => ${his.title}")
            }
            historyAdapter.addHistory(appointments)
        }.addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents: ", exception)
        }
    }
}