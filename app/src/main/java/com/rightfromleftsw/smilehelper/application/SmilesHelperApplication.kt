package com.rightfromleftsw.smilehelper.application

import com.rightfromleftsw.smilehelper.BuildConfig
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class SmilesHelperApplication: DaggerApplication() {

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerApplicationComponent.factory().create(this)
  }

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}