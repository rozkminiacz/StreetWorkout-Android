package tech.michalik.gymapp.listscreen

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import okhttp3.internal.EMPTY_RESPONSE
import retrofit2.HttpException
import retrofit2.Response
import tech.michalik.gymapp.dto.StreetWorkoutPlaces

/**
 * Created by jaroslawmichalik
 */
class StreetWorkoutListViewModelTest : StringSpec({

})