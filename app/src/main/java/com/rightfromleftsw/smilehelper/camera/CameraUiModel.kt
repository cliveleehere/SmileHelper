package com.rightfromleftsw.smilehelper.camera

import com.rightfromleftsw.smilehelper.analyzer.detector.Emotion
import com.rightfromleftsw.smilehelper.analyzer.detector.Face

data class CameraUiModel(val emotion: Emotion)

object FaceToCameraUiModelMapper {
  fun map(face: Face): CameraUiModel {
    return CameraUiModel(face.emotion)
  }
}