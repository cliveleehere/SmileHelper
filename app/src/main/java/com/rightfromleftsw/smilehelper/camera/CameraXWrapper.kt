package com.rightfromleftsw.smilehelper.camera

import android.util.Size
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraX
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import androidx.lifecycle.LifecycleOwner
import com.rightfromleftsw.smilehelper.R

class CameraXWrapper(
    private val owner: LifecycleOwner
) : CameraInterface {

  override val layoutId: Int = R.layout.activity_camerax

  override val viewFinderId: Int = R.id.camerax_view_finder

  override fun setupCamera(view: View) {
    view.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
      updateTransform()
    }
  }

  override fun startCamera(viewFinder: View) {
    require(viewFinder is TextureView)

    // Create configuration object for the viewfinder use case
    val previewConfig = PreviewConfig.Builder().apply {
      setTargetResolution(Size(640, 480))
    }.build()


    // Build the viewfinder use case
    val preview = Preview(previewConfig)

    // Every time the viewfinder is updated, recompute layout
    preview.setOnPreviewOutputUpdateListener {

      // To update the SurfaceTexture, we have to remove it and re-add it
      val parent = viewFinder.parent as ViewGroup
      parent.removeView(viewFinder)
      parent.addView(viewFinder, 0)

      viewFinder.surfaceTexture = it.surfaceTexture
      updateTransform()
    }

    // Bind use cases to lifecycle
    // If Android Studio complains about "this" being not a LifecycleOwner
    // try rebuilding the project or updating the appcompat dependency to
    // version 1.1.0 or higher.
    CameraX.bindToLifecycle(owner, preview)
  }

  override fun updateTransform() {
  }
}