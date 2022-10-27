package tech.michalik.gymapp.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class StreetWorkoutPlaceItemDto(
  @SerializedName("address")
    val address: String,
  @SerializedName("location")
    val location: LocationDto,
  @SerializedName("name")
    val name: String,
  @SerializedName("photo")
    val photo: String,
  @SerializedName("secondary_address")
    val secondaryAddress: String,
  @SerializedName("id")
    val id: String
)