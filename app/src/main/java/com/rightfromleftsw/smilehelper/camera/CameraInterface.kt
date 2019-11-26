package com.rightfromleftsw.smilehelper.camera

import android.view.View

interface CameraInterface {

  val layoutId: Int

  val viewFinderId: Int

  fun setupCamera(view: View)

  /**
   * viewFinder may be different subtype of View. e.g. SurfaceView or TextureView
   */
  fun startCamera(viewFinder: View)

  fun updateTransform()
}