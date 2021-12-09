package edu.sjsu.android.eclinic.models

data class Profile(
    val name: String = "",
    val specialty: String = "",
    val overview: String = "",
    val email: String = "",
    val username: String = "",
    val profile_image: String = "",
    val phone: String = "",
    val location: String = "",
)