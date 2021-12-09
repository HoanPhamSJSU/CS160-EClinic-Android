package edu.sjsu.android.eclinic.models

import java.util.*

class Appointment(
    var documentId: String = "",
    val doctor_name: String = "",
    val doctor_id: String = "",
    val doctor_specialty: String = "",
    val doctor_image: String = "",
    val date: Date? = Date(),
)