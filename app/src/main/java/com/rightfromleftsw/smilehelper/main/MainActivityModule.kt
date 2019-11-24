package com.rightfromleftsw.smilehelper.main

import android.content.Context
import com.rightfromleftsw.smilehelper.face.FaceDetectorWrapper
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

  @Provides
  fun faceDetector(context: Context) = FaceDetectorWrapper(context)
}