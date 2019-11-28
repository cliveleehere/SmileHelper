package com.rightfromleftsw.smilehelper.analyzer.detector

import android.media.Image
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import io.reactivex.Single

/**
 * Converts Firebase face detection to an observable type
 */
class FirebaseFaceDetector : FaceDetector {

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

  override fun detectFace(mediaImage: Image, degrees: Int): Single<List<Face>> {
    val imageRotation = degreesToFirebaseRotation(degrees)
    val image = FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)

    return Single.create<List<Face>> { emitter ->
      detector.detectInImage(image)
          .addOnSuccessListener { faces ->
            emitter.onSuccess(
                faces.map(FirebaseFaceToFaceMapper::map)
            )
          }.addOnFailureListener { e ->
            emitter.onError(e)
          }
    }
  }
}