package tech.michalik.gymapp.listscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import tech.michalik.gymapp.NetworkModule
import tech.michalik.gymapp.R
import timber.log.Timber

/**
 * Created by jaroslawmichalik
 */

object AndroidDispatcherFacade: DispatcherFacade{
  override val io: CoroutineDispatcher
    get() = Dispatchers.IO
  override val main: CoroutineDispatcher
    get() = Dispatchers.Main

}

@Composable
fun ItemsListScreen() {
  val viewModel = remember {
    StreetWorkoutListViewModel(
      api = NetworkModule().api
    )
  }

  val state = viewModel.state.collectAsState()

  when (val listState = state.value) {
    is ItemsListState.Error -> {
      ErrorState(listState = listState) {
        viewModel.loadData()
      }
    }
    ItemsListState.Loading -> {
      LoadingState()
    }
    is ItemsListState.Success -> {
      ListState(listState)
    }
  }
}

@Composable
private fun ListState(listState: ItemsListState.Success) {
  LazyColumn {
    items(listState.list) {
      StreetWorkoutItem(it)
    }
  }
}

@Preview
@Composable
fun ListStatePreview() {

  val items = (0..5).map {
    StreetWorkoutViewItem(
      title = "Title #$it",
      subtitle = "subtitle #$it",
      photo = "https://lh5.googleusercontent.com/p/AF1QipPJBXX-CjrRs1FZhSIHJMYx2aLVtTivKjehFSlb=w408-h272-k-no"
    )
  }

  ListState(
    listState = ItemsListState.Success(
      list = items
    )
  )
}

@Composable
private fun LoadingState() {
  Box(modifier = Modifier.fillMaxSize()) {
    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
  }
}

@Composable
private fun ErrorState(listState: ItemsListState.Error, onRetry: () -> Unit) {
  Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
    val modifier = Modifier.align(Alignment.CenterHorizontally)
    Row(modifier = modifier) {
      Text(
        text = stringResource(id = R.string.error_occurred),
        style = MaterialTheme.typography.h4
      )
    }
    Row(modifier = modifier) {
      Text(text = listState.message)
    }
    Row(modifier = modifier) {

      Button(onClick = {
        Timber.d("Retrying..")
        onRetry()
      }) {
        Text(text = stringResource(id = R.string.retry_action))
      }
    }
  }
}

@Preview
@Composable
fun ErrorStatePreview() {
  ErrorState(listState = ItemsListState.Error("nie udało się")) {}
}

@Preview
@Composable
fun LoadingStatePreview() {
  LoadingState()
}

data class StreetWorkoutViewItem(
  val title: String,
  val subtitle: String,
  val photo: String
)

@Composable
fun StreetWorkoutItem(item: StreetWorkoutViewItem) {

  Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.padding(8.dp)) {
    Box {
      GlideImage(imageModel = item.photo, modifier = Modifier.height(350.dp))

      Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .height(100.dp)
          .fillMaxWidth()
          .background(
            brush = Brush.verticalGradient(
              colors = listOf(
                Color.Transparent,
                Color.Black
              )
            )
          ),
      ) {
        Row(modifier = Modifier.padding(8.dp)) {
          Text(text = item.title, style = MaterialTheme.typography.h6, color = Color.White)
        }

        Row(modifier = Modifier.padding(8.dp)) {
          Text(text = item.subtitle, style = MaterialTheme.typography.caption, color = Color.White)
        }
      }
    }
  }
}