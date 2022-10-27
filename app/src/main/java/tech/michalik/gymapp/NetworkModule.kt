package tech.michalik.gymapp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import tech.michalik.gymapp.dto.StreetWorkoutPlaces

/**
 * Created by jaroslawmichalik
 */
class NetworkModule {

  private val okHttpClient = OkHttpClient()

  val api by lazy {
    Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(StreetWorkoutApi::class.java)
  }

  companion object {
    const val BASE_URL = "https://whispering-basin-27673.herokuapp.com/"
  }
}

interface StreetWorkoutApi {
  @GET("/")
  suspend fun fetchAll(): StreetWorkoutPlaces
}