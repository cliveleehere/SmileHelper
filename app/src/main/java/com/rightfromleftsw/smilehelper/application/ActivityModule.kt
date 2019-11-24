package com.rightfromleftsw.smilehelper.application

import com.rightfromleftsw.smilehelper.main.MainActivity
import com.rightfromleftsw.smilehelper.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
  @ContributesAndroidInjector(modules = [MainActivityModule::class])
  abstract fun bindMainActivity(): MainActivity
}