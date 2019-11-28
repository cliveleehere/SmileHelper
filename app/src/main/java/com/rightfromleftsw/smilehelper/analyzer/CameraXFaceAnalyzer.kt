package com.rightfromleftsw.smilehelper.analyzer

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.rightfromleftsw.smilehelper.analyzer.detector.Face
import com.rightfromleftsw.smilehelper.analyzer.detector.FaceDetector
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import timber.log.Timber

/**
 * Uses a [FaceDetector] in a CameraX Analyzer
 */
class CameraXFaceAnalyzer(
    private val faceDetector: FaceDetector
): ImageAnalysis.Analyzer, FaceAnalyzer {

  private val subject: Subject<List<Face>> = PublishSubject.create<List<Face>>()

  override fun analyze(imageProxy: ImageProxy?, degrees: Int) {
    if (imageProxy != null && imageProxy.image != null) {
      val mediaImage = imageProxy.image!!
      faceDetector.detectFace(mediaImage, degrees).toObservable()
          .subscribe(subject)
    } else {
      Timber.d("Null image")
    }
  }

  override fun faces(): Observable<List<Face>> = subject
}
