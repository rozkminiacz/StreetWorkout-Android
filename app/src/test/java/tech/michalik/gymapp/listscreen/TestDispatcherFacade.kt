package tech.michalik.gymapp.listscreen

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestDispatcherFacade(
  override val io: CoroutineDispatcher = UnconfinedTestDispatcher(),
  override val main: CoroutineDispatcher = UnconfinedTestDispatcher()
) : DispatcherFacade
