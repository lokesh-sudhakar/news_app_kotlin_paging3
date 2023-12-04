package com.technocraze.newsappassesment.network

import com.technocraze.newsappassesment.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


class AuthenticationInterceptor : Interceptor {

  companion object {
    private const val AUTHORIZATION_HEADER_KEY = "Authorization"
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request().newBuilder()
      .header(AUTHORIZATION_HEADER_KEY, BuildConfig.API_KEY)
      .build()

    val res = chain.proceed(request)
    return res
  }

}