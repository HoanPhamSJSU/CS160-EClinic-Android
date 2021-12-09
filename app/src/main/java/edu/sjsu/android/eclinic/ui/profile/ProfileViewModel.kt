package edu.sjsu.android.eclinic.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Appointment Fragment"
    }
    val text: LiveData<String> = _text
}