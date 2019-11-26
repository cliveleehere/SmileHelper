package com.rightfromleftsw.smilehelper.permissions

import android.Manifest
import android.app.Activity

class CameraPermissionDelegate(activity: Activity)
  : PermissionDelegate(activity) {
  override val requestCodePermissions = 10
  override val requiredPermissions = arrayOf(Manifest.permission.CAMERA)
}