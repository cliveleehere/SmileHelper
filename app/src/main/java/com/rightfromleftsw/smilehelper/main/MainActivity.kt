package com.rightfromleftsw.smilehelper.main

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.rightfromleftsw.smilehelper.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
class MainActivity : AppCompatActivity(), HasAndroidInjector {
  private lateinit var container: FrameLayout

  @Inject
  internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

  override fun androidInjector(): AndroidInjector<Any> {
    return androidInjector
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_main)
    container = findViewById(R.id.fragment_container)
  }

  override fun onResume() {
    super.onResume()
    // Before setting full screen flags, we must wait a bit to let UI settle; otherwise, we may
    // be trying to set app to immersive mode before it's ready and the flags do not stick
    container.postDelayed({
      container.systemUiVisibility = FLAGS_FULLSCREEN
    }, IMMERSIVE_FLAG_TIMEOUT)
  }

  companion object {
    const val FLAGS_FULLSCREEN =
        View.SYSTEM_UI_FLAG_LOW_PROFILE or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

    private const val IMMERSIVE_FLAG_TIMEOUT = 500L
  }
}
