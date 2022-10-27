package tech.michalik.gymapp.listscreen

sealed class ItemsListState {
  data class Success(val list: List<StreetWorkoutViewItem>) : ItemsListState()
  data class Error(val message: String) : ItemsListState()
  object Loading : ItemsListState()
}