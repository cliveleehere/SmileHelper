package com.rightfromleftsw.smilehelper.analyzer

import android.media.Image
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.rightfromleftsw.smilehelper.analyzer.detector.Face
import com.rightfromleftsw.smilehelper.analyzer.detector.FaceDetector
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Uses a [FaceDetector] in a CameraX Analyzer
 */
class CameraXFaceAnalyzer(
    private val faceDetector: FaceDetector
): ImageAnalysis.Analyzer, FaceAnalyzer {

  private val subject: Subject<Pair<Image, Int>> = PublishSubject.create()

  override fun analyze(imageProxy: ImageProxy?, degrees: Int) {
    if (imageProxy != null && imageProxy.image != null) {
      val mediaImage = imageProxy.image!!

      subject.onNext(mediaImage to degrees)
    } else {
      Timber.d("Null image")
    }
  }

  override fun faces(): Flowable<List<Face>> {
    return subject
        .throttleLatest(250, TimeUnit.MILLISECONDS)
        .toFlowable(BackpressureStrategy.DROP)
        .flatMap {
          val mediaImage = it.first
          val degrees = it.second
          faceDetector.detectFace(mediaImage, degrees).toFlowable()
        }
  }
}
