package edu.sjsu.android.eclinic.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import edu.sjsu.android.eclinic.R

class EclinicNavigation {
    companion object {
        fun toProfile(fragment: Fragment) {
            fragment.findNavController().navigate(R.id.nav_profile)
        }

        fun toAppointments(fragment: Fragment) {
            fragment.findNavController().navigate(R.id.nav_appointment)
        }

        fun toLogin(fragment: Fragment) {
            fragment.findNavController().navigate(R.id.nav_login)
        }

        fun toHome(fragment: Fragment) {
            fragment.findNavController().navigate(R.id.action_loginFragment_to_nav_home)
        }

        fun toDoctorDetail(fragment: Fragment, extras: FragmentNavigator.Extras?) {
            fragment.findNavController().navigate(R.id.nav_doctor_details, null, null, extras)
        }

        fun toInfo(fragment: Fragment) {
            fragment.findNavController().navigate(R.id.nav_info)
        }
    }
}