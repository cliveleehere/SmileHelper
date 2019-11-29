package com.rightfromleftsw.smilehelper.analyzer


import com.rightfromleftsw.smilehelper.analyzer.detector.Face
import io.reactivex.Flowable

interface FaceAnalyzer {
  fun faces(): Flowable<List<Face>>
}