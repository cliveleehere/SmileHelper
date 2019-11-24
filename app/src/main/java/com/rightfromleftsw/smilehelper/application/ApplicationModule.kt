package com.rightfromleftsw.smilehelper.application

import android.content.Context
import com.rightfromleftsw.smilehelper.di.AppScope
import dagger.Module
import dagger.Provides

@Module
object ApplicationModule {
  @Provides
  @AppScope
  @JvmStatic
  fun appContext(app: SmilesHelperApplication): Context = app.applicationContext
}