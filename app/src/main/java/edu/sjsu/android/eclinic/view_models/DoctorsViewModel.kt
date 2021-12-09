package edu.sjsu.android.eclinic.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.sjsu.android.eclinic.models.Doctor

class DoctorsViewModel : ViewModel() {
    val selected = MutableLiveData<Doctor>()

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    fun select(item: Doctor) {
        selected.value = item
    }

    fun setTitle(newTitle: String) {
        title.value = newTitle
    }

    fun setContent(newContent: String) {
        content.value = newContent
    }
}