package com.rightfromleftsw.smilehelper.analyzer.detector

import android.graphics.Rect
import android.media.Image
import io.reactivex.Single

/**
 * Given a single image, detect any faces in it
 */
interface FaceDetector {
  fun detectFace(mediaImage: Image, degrees: Int): Single<List<Face>>
}

data class Face(val boundingBox: Rect, val emotion: Emotion)

enum class Emotion {
  HAPPY, NEUTRAL
}