package com.rightfromleftsw.smilehelper.camera

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rightfromleftsw.smilehelper.permissions.PermissionDelegate
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CameraFragment: Fragment() {

  @Inject
  lateinit var cameraPermissionsDelegate: PermissionDelegate

  @Inject
  lateinit var cameraWrapper: CameraInterface

  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    val view = inflater.inflate(cameraWrapper.layoutId, container, false)
    cameraWrapper.setupCamera(view)

    if (cameraPermissionsDelegate.isAllowedElseRequest()) {
      cameraWrapper.startCamera()
    }

    return view
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (cameraPermissionsDelegate.onRequestPermissionResult(requestCode)) {
      cameraWrapper.startCamera()
    }
  }
}