package com.rightfromleftsw.smilehelper.main

import android.os.Bundle
import com.rightfromleftsw.smilehelper.permissions.CameraPermissionDelegate
import dagger.android.DaggerActivity
import javax.inject.Inject

/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
class MainActivity : DaggerActivity() {

  @Inject
  lateinit var cameraPermissionsDelegate: CameraPermissionDelegate

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (cameraPermissionsDelegate.checkAndRequest()) {
      startCamera()
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    if (cameraPermissionsDelegate.onRequestPermissionResult(requestCode)) {
      startCamera()
    }
  }

  fun startCamera() {
  }

  companion object {
    private val TAG = MainActivity::class.java.simpleName
  }
}
