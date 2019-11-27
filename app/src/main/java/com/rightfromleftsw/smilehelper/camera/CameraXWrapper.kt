package com.rightfromleftsw.smilehelper.camera

import android.graphics.Matrix
import android.util.DisplayMetrics
import android.util.Size
import android.view.*
import androidx.camera.core.CameraX
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import androidx.lifecycle.LifecycleOwner
import com.rightfromleftsw.smilehelper.R
import timber.log.Timber

class CameraXWrapper(
    private val owner: LifecycleOwner
) : CameraInterface {

  override val layoutId: Int = R.layout.camerax_view_finder

  private lateinit var viewFinder: TextureView

  override fun setupCamera(containerView: View) {
    viewFinder = containerView.findViewById(R.id.view_finder)
    viewFinder.addOnLayoutChangeListener { _, left, top, right, bottom,
                                           _, _, _, _ ->

      val newDimens = Size(right - left, bottom - top)
      Timber.d("Viewfinder layout changed. Size $newDimens")

      updateTransform()
    }
  }

  override fun startCamera() {
    if (!::viewFinder.isInitialized) {
      Timber.e("setupCamera not called before startCamera")
    }

    viewFinder.post {
      val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }

      // Create configuration object for the viewfinder use case
      val previewConfig = PreviewConfig.Builder().apply {
        setTargetResolution(Size(metrics.widthPixels, metrics.heightPixels))
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
      CameraX.bindToLifecycle(owner, preview)
    }

  }

  private fun updateTransform() {
    if (!::viewFinder.isInitialized) {
      Timber.e("setupCamera not called before startCamera")
    }

    val matrix = Matrix()

    // Compute the center of the view finder
    val centerX = viewFinder.width / 2f
    val centerY = viewFinder.height / 2f

    // Correct preview output to account for display rotation
    val rotationDegrees = getDisplaySurfaceRotation(viewFinder.display) ?: return
    matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

    // Finally, apply transformations to our TextureView
    viewFinder.setTransform(matrix)
  }

  companion object {
    fun getDisplaySurfaceRotation(display: Display): Int? = when (display.rotation) {
      Surface.ROTATION_0 -> 0
      Surface.ROTATION_90 -> 90
      Surface.ROTATION_180 -> 180
      Surface.ROTATION_270 -> 270
      else -> null
    }
  }
}