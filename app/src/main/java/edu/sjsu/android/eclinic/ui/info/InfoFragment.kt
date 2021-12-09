package edu.sjsu.android.eclinic.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import edu.sjsu.android.eclinic.MainActivity
import edu.sjsu.android.eclinic.databinding.FragmentInfoBinding
import edu.sjsu.android.eclinic.view_models.DoctorsViewModel

class InfoFragment : Fragment() {
    // TAG
    private val TAG = "Home Fragment"

    // Main Activity
    private lateinit var mainActivity: MainActivity

    // Fragment Binding
    private lateinit var binding: FragmentInfoBinding

    // View Model
    private val model: DoctorsViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(inf: LayoutInflater, ctn: ViewGroup?, state: Bundle?): View {
        binding = FragmentInfoBinding.inflate(inf, ctn, false)
        mainActivity.setToolbar(binding.toolbar)
        binding.textTitle.text = "${model.title.value}"
        binding.textInfo.text = "${model.content.value}"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}