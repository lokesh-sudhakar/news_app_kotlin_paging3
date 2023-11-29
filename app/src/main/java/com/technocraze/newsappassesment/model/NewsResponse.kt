package com.technocraze.newsappassesment.model



data class NewsResponse(
  val articles: List<Article>?=null,
  val status: String,
  val totalResults: Int?=null,
  val code: String?=null,
  val message: String? = null,
)



sealed class Status(val value:String){
  data object OK:Status("ok")
  data object ERROR:Status("error")
}