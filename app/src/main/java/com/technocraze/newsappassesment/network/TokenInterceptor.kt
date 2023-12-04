package com.technocraze.newsappassesment.network

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor:Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {


    var request = chain.request();

    // try the request
    var response = chain.proceed(request);

    if (!response.isSuccessful) {
      // close previous response
      response.close()

      // get a new token (I use a synchronous Retrofit call)

      // create a new request and modify it accordingly using the new token
      var newRequest = request.newBuilder().build()

      // retry the request
      return chain.proceed(newRequest);
    }

    // otherwise just pass the original response on
    return response;
  }
}
