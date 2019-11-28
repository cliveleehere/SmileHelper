package com.rightfromleftsw.smilehelper.main

import com.rightfromleftsw.smilehelper.permissions.CameraPermissionDelegate
import com.rightfromleftsw.smilehelper.permissions.PermissionDelegate
import dagger.Module
import dagger.Provides

@Module(includes = [FragmentModule::class])
object MainActivityModule {

  @Provides
  fun cameraPermissionDelegate(mainActivity: MainActivity): PermissionDelegate
      = CameraPermissionDelegate(mainActivity)
}