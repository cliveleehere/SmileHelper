package com.rightfromleftsw.smilehelper.main

import com.rightfromleftsw.smilehelper.permissions.CameraPermissionDelegate
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

  @Provides
  fun cameraPermissionDelegate(mainActivity: MainActivity) = CameraPermissionDelegate(mainActivity)
}