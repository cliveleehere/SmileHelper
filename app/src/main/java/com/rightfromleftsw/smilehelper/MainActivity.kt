/*
 * Copyright 2018 Google LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rightfromleftsw.smilehelper

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.core.Session
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.rightfromleftsw.smilehelper.face.FaceDetectorWrapper

/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
class MainActivity : AppCompatActivity() {

  private lateinit var arFragment: ArFragment
  private var kangarooModel: ModelRenderable? = null

  private lateinit var faceDetector: FaceDetectorWrapper

  private lateinit var session: Session

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    faceDetector = FaceDetectorWrapper(applicationContext)

    if (!checkIsSupportedDeviceOrFinish(this)) {
      return
    }

    setContentView(R.layout.activity_ux)
    arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment

    // When you build a Renderable, Sceneform loads its resources in the background while returning
    // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
    ModelRenderable.builder()
        .setSource(this, R.raw.kangaroo)
        .build()
        .thenAccept { renderable -> kangarooModel = renderable }
        .exceptionally {
          val toast = Toast.makeText(this, "Unable to load kangaroo renderable", Toast.LENGTH_LONG)
          toast.setGravity(Gravity.CENTER, 0, 0)
          toast.show()
          null
        }

    arFragment.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, motionEvent: MotionEvent ->
      if (kangarooModel == null) {
        return@setOnTapArPlaneListener
      }

      // Create the Anchor.
      val anchor = hitResult.createAnchor()
      val anchorNode = AnchorNode(anchor)
      anchorNode.setParent(arFragment.arSceneView?.scene)

      // Create the transformable and add it to the anchor.
      val kangaroo = TransformableNode(arFragment.transformationSystem)
      kangaroo.setParent(anchorNode)
      kangaroo.renderable = kangarooModel
      kangaroo.select()
    }

    arFragment.arSceneView.scene.addOnUpdateListener {
      Log.d("FRAME TIME", it.toString())
    }
  }

  companion object {
    private val TAG = MainActivity::class.java.simpleName
    private val MIN_OPENGL_VERSION = 3.0

    /**
     * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
     * on this device.
     *
     *
     * Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
     *
     *
     * Finishes the activity if Sceneform can not run
     */
    fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
      val openGlVersionString = (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
          .deviceConfigurationInfo
          .glEsVersion
      if (java.lang.Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
        Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later")
        Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
            .show()
        activity.finish()
        return false
      }
      return true
    }
  }
}
