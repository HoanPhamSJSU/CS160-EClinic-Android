package edu.sjsu.android.eclinic.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.sjsu.android.eclinic.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inf: LayoutInflater, ctn: ViewGroup?, state: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inf, ctn, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}