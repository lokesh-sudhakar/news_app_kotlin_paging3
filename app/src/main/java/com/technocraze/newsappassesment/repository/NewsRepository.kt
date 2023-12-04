package com.technocraze.newsappassesment.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.technocraze.newsappassesment.model.Article

interface NewsRepository {

  fun getNews(category:String): LiveData<PagingData<Article>>

  suspend fun getFiles(times:Int): String

}