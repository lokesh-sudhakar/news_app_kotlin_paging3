package com.technocraze.newsappassesment.di

import com.technocraze.newsappassesment.BuildConfig
import com.technocraze.newsappassesment.network.AuthenticationInterceptor
import com.technocraze.newsappassesment.network.NewsApiService
import com.technocraze.newsappassesment.repository.NewsRepository
import com.technocraze.newsappassesment.repository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

  companion object {



    @Provides
    @Singleton
    fun getOkhttpInstance(
      loggingInterceptor: HttpLoggingInterceptor,
      authenticationInterceptor: AuthenticationInterceptor
    ): OkHttpClient {
      return OkHttpClient.Builder()
        .addInterceptor(authenticationInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    }


    @Provides
    @Singleton
    fun getAuthInterceptor(): AuthenticationInterceptor {
      return AuthenticationInterceptor()
    }


    @Provides
    @Singleton
    fun getLoggingInterceptor(): HttpLoggingInterceptor {
      val loggingInterceptor = HttpLoggingInterceptor()
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
      return loggingInterceptor
    }


    @Provides
    @Singleton
    fun getRetrofitInstance(client: OkHttpClient): Retrofit {
      val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_API_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

      return retrofit
    }

    @Provides
    @Singleton
    fun getNewsApiService(retrofit: Retrofit): NewsApiService {
      return retrofit.create(NewsApiService::class.java)
    }
  }


  @Binds
  abstract fun getNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository


}