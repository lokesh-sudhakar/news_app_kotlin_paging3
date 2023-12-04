package com.technocraze.newsappassesment.network

import com.technocraze.newsappassesment.model.NewsResponse
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.Query

interface NewsApiService {

  @GET("/v2/everything")
  @Headers( "Accept: application/json",
    "User-Agent: Your-App-Name",
    "Cache-Control: max-age=640000")
  suspend fun getLatestNews(
    @Query("q") queryTopic: String = "bitcoin",
    @Query("page") page: Int,
    @Query("pageSize") pageSize: Int,
    ): NewsResponse

  @Multipart()
  suspend fun uploadImage(
    @Part image: MultipartBody.Part
  )

  fun fetResults(): String
  fun fetchData(): Int

}