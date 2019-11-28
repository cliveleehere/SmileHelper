package com.rightfromleftsw.smilehelper.analyzer


import com.rightfromleftsw.smilehelper.analyzer.detector.Face
import io.reactivex.Observable

interface FaceAnalyzer {
  fun faces(): Observable<List<Face>>
}