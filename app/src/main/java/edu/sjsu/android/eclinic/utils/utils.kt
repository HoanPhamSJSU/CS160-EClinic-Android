package edu.sjsu.android.eclinic.utils

import com.google.android.gms.maps.model.LatLng

class utils {
    companion object {
        fun getLocation(locationString: String): LatLng {
            val value = locationString.split(",")
            val lat = value.get(0).toDoubleOrNull()
            val long = value.get(1).toDoubleOrNull()
            if (lat != null && long != null)
                return LatLng(lat, long)
            return LatLng(0.0, 0.0)
        }
    }
}