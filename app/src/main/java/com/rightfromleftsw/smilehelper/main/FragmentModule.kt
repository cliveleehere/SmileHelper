package com.rightfromleftsw.smilehelper.main

import com.rightfromleftsw.smilehelper.camera.CameraFragment
import com.rightfromleftsw.smilehelper.camera.CameraFragmentModule
import com.rightfromleftsw.smilehelper.di.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

  @FragmentScope
  @ContributesAndroidInjector(modules = [CameraFragmentModule::class])
  abstract fun injectCameraFragment(): CameraFragment
}