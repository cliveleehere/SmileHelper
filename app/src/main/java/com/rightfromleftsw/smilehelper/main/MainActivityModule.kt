package com.rightfromleftsw.smilehelper.main

import com.rightfromleftsw.smilehelper.camera.CameraInterface
import com.rightfromleftsw.smilehelper.camera.CameraXWrapper
import com.rightfromleftsw.smilehelper.permissions.CameraPermissionDelegate
import com.rightfromleftsw.smilehelper.permissions.PermissionDelegate
import dagger.Module
import dagger.Provides

@Module
object MainActivityModule {

  @Provides
  fun cameraPermissionDelegate(mainActivity: MainActivity): PermissionDelegate
      = CameraPermissionDelegate(mainActivity)

  @Provides
  fun cameraX(mainActivity: MainActivity): CameraInterface = CameraXWrapper(mainActivity)
}