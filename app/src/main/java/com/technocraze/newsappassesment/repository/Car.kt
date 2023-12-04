package com.technocraze.newsappassesment.repository


import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class Car  @Inject constructor(@ActivityContext var  context: Context) {

  @EntryPoint
  @InstallIn(SingletonComponent::class)
  interface WheelsEntryPoint{
    fun wheel():Wheel
  }

  fun getWheel(appContext: Context):Wheel{
    var entryPoint = EntryPointAccessors.fromApplication(
      appContext,WheelsEntryPoint::class.java
    )
    return entryPoint.wheel()
  }

  fun getName():String{
    return getWheel(context).toString()
  }

}