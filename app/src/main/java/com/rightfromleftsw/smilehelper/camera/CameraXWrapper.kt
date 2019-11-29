package com.rightfromleftsw.smilehelper.camera

import android.graphics.Matrix
import android.util.DisplayMetrics
import android.util.Size
import android.view.*
import androidx.camera.core.*
import androidx.lifecycle.LifecycleOwner
import com.rightfromleftsw.smilehelper.BuildConfig
import com.rightfromleftsw.smilehelper.R
import com.rightfromleftsw.smilehelper.analyzer.FaceAnalyzer
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import timber.log.Timber
import java.util.concurrent.Executors

class CameraXWrapper(
    private val owner: LifecycleOwner,
    private val imageAnalyzer: ImageAnalysis.Analyzer,
    private val faceAnalyzer: FaceAnalyzer
) : CameraInterface {

  private val executor = Executors.newSingleThreadExecutor()

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

  override fun startCamera(): Flowable<List<CameraUiModel>> {
    if (!::viewFinder.isInitialized) {
      Timber.e("setupCamera not called before startCamera")
    }

    val lensFacing = if (BuildConfig.DEBUG) CameraX.LensFacing.FRONT else CameraX.LensFacing.BACK

    val processor = PublishProcessor.create<List<CameraUiModel>> ()

    viewFinder.post {
      val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }

      // Create configuration object for the viewfinder use case
      val previewConfig = PreviewConfig.Builder().apply {
        setTargetAspectRatio(AspectRatio.RATIO_16_9)
        setLensFacing(lensFacing)
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

      val analyzerConfig = ImageAnalysisConfig.Builder().apply {
        setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
        setLensFacing(lensFacing)
      }.build()

      val analyzerUseCase = ImageAnalysis(analyzerConfig).apply {
        setAnalyzer(executor, imageAnalyzer)
      }

      // Bind use cases to lifecycle
      CameraX.bindToLifecycle(owner, preview, analyzerUseCase)

      faceAnalyzer.faces()
          .map { faces ->
            faces.map { face ->
              FaceToCameraUiModelMapper.map(face)
            }
          }
          .subscribe(processor)
    }

    return processor
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