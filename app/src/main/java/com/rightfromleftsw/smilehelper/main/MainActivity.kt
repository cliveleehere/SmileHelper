package com.rightfromleftsw.smilehelper.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rightfromleftsw.smilehelper.camera.CameraInterface
import com.rightfromleftsw.smilehelper.permissions.PermissionDelegate
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var cameraPermissionsDelegate: PermissionDelegate

  @Inject
  lateinit var cameraWrapper: CameraInterface

  private lateinit var cameraView: View

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)

    setContentView(cameraWrapper.layoutId)
    cameraView = findViewById(cameraWrapper.viewFinderId)

    cameraWrapper.setupCamera(cameraView)

    if (cameraPermissionsDelegate.checkAndRequest()) {
      cameraWrapper.startCamera(cameraView)
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    if (cameraPermissionsDelegate.onRequestPermissionResult(requestCode)) {
      cameraWrapper.startCamera(cameraView)
    }
  }

  companion object {
    private val TAG = MainActivity::class.java.simpleName
  }
}
