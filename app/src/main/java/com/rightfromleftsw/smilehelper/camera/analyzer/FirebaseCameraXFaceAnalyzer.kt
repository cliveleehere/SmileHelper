package com.rightfromleftsw.smilehelper.camera.analyzer

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import timber.log.Timber

// From https://firebase.google.com/docs/ml-kit/android/detect-faces
class FirebaseCameraXFaceAnalyzer: ImageAnalysis.Analyzer {

  private val detector: FirebaseVisionFaceDetector

  init {
    val options = FirebaseVisionFaceDetectorOptions.Builder()
        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
        .build()

    detector = FirebaseVision.getInstance().getVisionFaceDetector(options)
  }

  private fun degreesToFirebaseRotation(degrees: Int): Int = when(degrees) {
    0 -> FirebaseVisionImageMetadata.ROTATION_0
    90 -> FirebaseVisionImageMetadata.ROTATION_90
    180 -> FirebaseVisionImageMetadata.ROTATION_180
    270 -> FirebaseVisionImageMetadata.ROTATION_270
    else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
  }

  override fun analyze(imageProxy: ImageProxy?, degrees: Int) {
    val mediaImage = imageProxy?.image
    val imageRotation = degreesToFirebaseRotation(degrees)
    if (mediaImage != null) {
      val image = FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)

      detector.detectInImage(image)
          .addOnSuccessListener { faces ->
            faces.forEach {
              if (it.smilingProbability > 0.8) {
                Timber.d("Smiling!")
              }
            }
          }
          .addOnFailureListener { e ->
            Timber.e(e)
          }
    }
  }
}