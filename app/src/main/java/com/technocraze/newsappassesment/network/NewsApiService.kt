package com.technocraze.newsappassesment.network

import com.technocraze.newsappassesment.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

  @GET("/v2/everything")
  suspend fun getLatestNews(
    @Query("q") queryTopic: String = "bitcoin",
    @Query("page") page: Int,
    @Query("pageSize") pageSize: Int,
    ): NewsResponse


}