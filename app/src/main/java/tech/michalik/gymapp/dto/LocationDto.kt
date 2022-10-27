package tech.michalik.gymapp.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LocationDto(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)