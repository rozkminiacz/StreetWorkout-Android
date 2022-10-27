package tech.michalik.gymapp

import android.app.Application
import timber.log.Timber

/**
 * Created by jaroslawmichalik
 */
class App: Application() {
  override fun onCreate() {
    super.onCreate()

    Timber.plant(Timber.DebugTree())
  }
}