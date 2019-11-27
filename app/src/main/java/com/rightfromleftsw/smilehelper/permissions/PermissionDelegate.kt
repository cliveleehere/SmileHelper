package com.rightfromleftsw.smilehelper.permissions

import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

abstract class PermissionDelegate(private val activity: Activity) {

  abstract val requestCodePermissions: Int
  abstract val requiredPermissions: Array<String>

  private fun permissionsGranted(): Boolean = requiredPermissions.all {
    ContextCompat.checkSelfPermission(activity.baseContext, it) == PackageManager.PERMISSION_GRANTED
  }

  /**
   * Returns true if permissions was already granted.
   * If not, requests permission and returns false.
   * Typically called in onCreate()
   */
  fun isAllowedElseRequest(): Boolean =
      if (permissionsGranted()) true
      else {
        ActivityCompat.requestPermissions(activity, requiredPermissions, requestCodePermissions)
        false
      }

  /**
   * Returns true if requestCode matches and all permissions were granted.
   * Otherwise, show a Toast and return false
   * Call from onRequestPermissionResult
   */
  fun onRequestPermissionResult(requestCode: Int): Boolean =
      if (requestCode == requestCodePermissions && permissionsGranted()) true
      else {
        Toast.makeText(activity,
            "Permissions not granted by the user.",
            Toast.LENGTH_SHORT).show()
        false
      }
}