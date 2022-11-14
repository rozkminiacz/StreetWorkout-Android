package tech.michalik.gymapp.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import tech.michalik.gymapp.StreetWorkoutApi
import tech.michalik.gymapp.listscreen.ItemsListState.Success
import timber.log.Timber

/**
 * Created by jaroslawmichalik
 */
class StreetWorkoutListViewModel(
  private val api: StreetWorkoutApi
) : ViewModel() {

  val state = MutableStateFlow<ItemsListState>(ItemsListState.Loading)

  fun loadData() {
    viewModelScope.launch(Dispatchers.IO) {
      try {

        state.value = ItemsListState.Loading

        val items = api.fetchAll()

        state.value = items
          .map { StreetWorkoutViewItem(title = it.name, subtitle = it.address, photo = it.photo) }
          .let(::Success)

      } catch (exception: HttpException) {

        Timber.e(exception, "Something failed")
        state.value = ItemsListState.Error(message = exception.message.orEmpty())
      }
    }
  }

  init {
    loadData()
  }
}