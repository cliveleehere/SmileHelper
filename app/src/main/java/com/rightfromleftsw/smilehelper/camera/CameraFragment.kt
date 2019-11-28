package com.rightfromleftsw.smilehelper.camera

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rightfromleftsw.smilehelper.analyzer.detector.Emotion
import com.rightfromleftsw.smilehelper.permissions.PermissionDelegate
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CameraFragment: Fragment() {

  @Inject
  lateinit var cameraPermissionsDelegate: PermissionDelegate

  @Inject
  lateinit var camera: CameraInterface

  private val compositeDisposable: CompositeDisposable = CompositeDisposable()

  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    val view = inflater.inflate(camera.layoutId, container, false)
    camera.setupCamera(view)

    if (cameraPermissionsDelegate.isAllowedElseRequest()) {
      startCamera()
    }

    return view
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (cameraPermissionsDelegate.onRequestPermissionResult(requestCode)) {
      startCamera()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    compositeDisposable.clear()
  }

  private fun startCamera() {
    compositeDisposable.add(camera.startCamera()
        .debounce(2000, TimeUnit.SECONDS, Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ models ->
          models.firstOrNull {
            it.emotion == Emotion.HAPPY
          }?.let {
            Toast.makeText(requireContext(), "Smile found!", Toast.LENGTH_SHORT)
          }
        }) {
          Timber.w("startCamera didn't return anything useful")
        })
  }
}