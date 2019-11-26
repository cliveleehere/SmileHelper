package com.rightfromleftsw.smilehelper.application

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(modules = [
  AndroidInjectionModule::class,
  ApplicationModule::class,
  ActivityModule::class
])
interface ApplicationComponent: AndroidInjector<SmilesHelperApplication> {
  @Component.Factory
  interface Factory: AndroidInjector.Factory<SmilesHelperApplication>
}