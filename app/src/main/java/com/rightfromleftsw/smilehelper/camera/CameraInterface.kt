package com.rightfromleftsw.smilehelper.camera

import android.view.View
import io.reactivex.Flowable

interface CameraInterface {

  val layoutId: Int

  fun setupCamera(containerView: View)

  fun startCamera(): Flowable<List<CameraUiModel>>
}