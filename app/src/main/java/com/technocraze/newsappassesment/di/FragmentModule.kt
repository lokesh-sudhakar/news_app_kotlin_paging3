package com.technocraze.newsappassesment.di

import com.technocraze.newsappassesment.repository.Car
import com.technocraze.newsappassesment.repository.Wheel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Singleton

@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {

  // @ActivityScoped
  // @Provides
  // fun getCar(wheel: Wheel): Car {
  //   return Car(wheel)
  // }
}