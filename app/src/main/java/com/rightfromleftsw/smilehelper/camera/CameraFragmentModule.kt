package com.rightfromleftsw.smilehelper.camera

import androidx.camera.core.ImageAnalysis
import com.rightfromleftsw.smilehelper.analyzer.CameraXFaceAnalyzer
import com.rightfromleftsw.smilehelper.analyzer.FaceAnalyzer
import com.rightfromleftsw.smilehelper.analyzer.detector.FaceDetector
import com.rightfromleftsw.smilehelper.analyzer.detector.FirebaseFaceDetector
import com.rightfromleftsw.smilehelper.di.FragmentScope
import dagger.Module
import dagger.Provides

@Module
object CameraFragmentModule {

  @Provides
  fun faceDetector(): FaceDetector = FirebaseFaceDetector()

  @Provides
  @FragmentScope
  fun cameraXFaceAnalyzer(faceDetector: FaceDetector): CameraXFaceAnalyzer =
      CameraXFaceAnalyzer(faceDetector)

  @Provides
  fun analyzer(analyzer: CameraXFaceAnalyzer): ImageAnalysis.Analyzer = analyzer

  @Provides
  fun faceAnalyzer(analyzer: CameraXFaceAnalyzer): FaceAnalyzer = analyzer

  @Provides
  fun cameraX(fragment: CameraFragment,
              analyzer: ImageAnalysis.Analyzer,
              faceAnalyzer: FaceAnalyzer): CameraInterface =
      CameraXWrapper(fragment, analyzer, faceAnalyzer)
}