package com.rightfromleftsw.smilehelper.application

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class SmilesHelperApplication: DaggerApplication() {

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerApplicationComponent.factory().create(this)
  }
}