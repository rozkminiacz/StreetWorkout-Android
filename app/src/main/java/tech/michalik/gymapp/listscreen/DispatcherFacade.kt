package tech.michalik.gymapp.listscreen

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherFacade{
  val io: CoroutineDispatcher
  val main: CoroutineDispatcher
}