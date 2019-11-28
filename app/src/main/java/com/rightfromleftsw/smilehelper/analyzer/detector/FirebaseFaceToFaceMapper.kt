package com.rightfromleftsw.smilehelper.analyzer.detector

import com.google.firebase.ml.vision.face.FirebaseVisionFace

object FirebaseFaceToFaceMapper {
  fun map(firebaseFace: FirebaseVisionFace): Face {
    return Face(
        boundingBox = firebaseFace.boundingBox,
        emotion =
        if (firebaseFace.smilingProbability > 0.8)
          Emotion.HAPPY
        else Emotion.NEUTRAL
    )
  }
}