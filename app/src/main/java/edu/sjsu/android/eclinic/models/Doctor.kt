package edu.sjsu.android.eclinic.models

data class Doctor(
    var documentId: String = "",
    val id: Long = 0,
    val name: String = "",
    val specialty: String = "",
    val backdrop_path: String = "",
    val poster_path: String = "",
    val overview: String = "",
    val location: String = "0,0",
    val review_count: Int = 0,
    val rating: Float = 0.0f,
    val total_patient: Int = 0,
    val experience: Int = 0,
)