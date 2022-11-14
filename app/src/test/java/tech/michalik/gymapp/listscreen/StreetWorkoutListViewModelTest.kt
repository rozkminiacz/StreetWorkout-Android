package tech.michalik.gymapp.listscreen

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.StandardTestDispatcher
import okhttp3.internal.EMPTY_RESPONSE
import retrofit2.HttpException
import retrofit2.Response
import tech.michalik.gymapp.StreetWorkoutApi
import tech.michalik.gymapp.dto.LocationDto
import tech.michalik.gymapp.dto.StreetWorkoutPlaceItemDto
import tech.michalik.gymapp.dto.StreetWorkoutPlaces

/**
 * Created by jaroslawmichalik
 */
class StreetWorkoutListViewModelTest : StringSpec({
  "given api emits error when load data then set error state"{
    val givenException = HttpException(Response.error<Any>(500, EMPTY_RESPONSE))

    val api: StreetWorkoutApi = object : StreetWorkoutApi {
      override suspend fun fetchAll(): StreetWorkoutPlaces {
        throw givenException
      }
    }

    val viewModel = StreetWorkoutListViewModel(
      api = api,
      dispatcherFacade = TestDispatcherFacade()
    )

    viewModel.state.value shouldBe ItemsListState.Error("Network error")
  }

  "first loading, then error"{
    val givenException = HttpException(Response.error<Any>(500, EMPTY_RESPONSE))

    val api: StreetWorkoutApi = object : StreetWorkoutApi {
      override suspend fun fetchAll(): StreetWorkoutPlaces {
        throw givenException
      }
    }

    val standardTestDispatcher = StandardTestDispatcher()

    val viewModel = StreetWorkoutListViewModel(
      api = api,
      dispatcherFacade = TestDispatcherFacade(
        io = standardTestDispatcher,
        main = standardTestDispatcher
      )
    )

    viewModel.state.value shouldBe ItemsListState.Loading

    standardTestDispatcher.scheduler.runCurrent()

    viewModel.state.value shouldBe ItemsListState.Error("Network error")
  }

  "first loading, then success"{

    val api: StreetWorkoutApi = object : StreetWorkoutApi {
      override suspend fun fetchAll(): StreetWorkoutPlaces {
        return StreetWorkoutPlaces()
          .also {
            it.addAll(generate(0))
          }
      }
    }

    val standardTestDispatcher = StandardTestDispatcher()

    val viewModel = StreetWorkoutListViewModel(
      api = api,
      dispatcherFacade = TestDispatcherFacade(
        io = standardTestDispatcher,
        main = standardTestDispatcher
      )
    )

    viewModel.state.value shouldBe ItemsListState.Loading

    standardTestDispatcher.scheduler.runCurrent()

    val listState = viewModel.state.value

    require(listState is ItemsListState.Success)

    listState.list shouldHaveSize 0
  }

  "given api emits 11 items when load data then emit success state"{
    val api: StreetWorkoutApi = object : StreetWorkoutApi {
      override suspend fun fetchAll(): StreetWorkoutPlaces {
        return StreetWorkoutPlaces()
          .also {
            it.addAll(generate(11))
          }
      }
    }

    val viewModel = StreetWorkoutListViewModel(
      api = api,
      dispatcherFacade = TestDispatcherFacade()
    )

    val listState = viewModel.state.value

    require(listState is ItemsListState.Success)

    listState.list shouldHaveSize 11
  }

})

fun generate(count: Int = 11) = (1..count).map {
  StreetWorkoutPlaceItemDto(
    address = "asd $it",
    location = LocationDto(0.0, 0.0),
    photo = "http://cdn.pl/asd-$it",
    secondaryAddress = "dsa $it",
    id = "$it",
    name = "Miejsce #$it"
  )
}