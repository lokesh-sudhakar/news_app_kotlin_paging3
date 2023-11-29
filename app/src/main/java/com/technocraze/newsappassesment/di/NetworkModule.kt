package com.technocraze.newsappassesment.di

import com.technocraze.newsappassesment.BuildConfig
import com.technocraze.newsappassesment.utils.Constants
import com.technocraze.newsappassesment.network.AuthenticationInterceptor
import com.technocraze.newsappassesment.network.NewsApiService
import com.technocraze.newsappassesment.repository.NewsRepository
import com.technocraze.newsappassesment.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Singleton
  @Provides
  fun getOkhttpInstance(loggingInterceptor: HttpLoggingInterceptor,
                        authenticationInterceptor: AuthenticationInterceptor
                        ): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(authenticationInterceptor)
      .addInterceptor(loggingInterceptor)
      .build()

  }

  @Singleton
  @Provides
  fun getAuthInterceptor(): AuthenticationInterceptor {
    return AuthenticationInterceptor()
  }

  @Singleton
  @Provides
  fun getLoggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return loggingInterceptor
  }

  @Singleton
  @Provides
  fun getRetrofitInstance(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_API_URL)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun getNewsApiService(retrofit: Retrofit): NewsApiService {
    return retrofit.create(NewsApiService::class.java)
  }


  @Provides
  @Singleton
  fun getNewsRepository(newsApiService: NewsApiService): NewsRepository {
    return NewsRepositoryImpl(newsApiService)
  }


}