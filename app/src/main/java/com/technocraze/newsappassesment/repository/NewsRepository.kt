package com.technocraze.newsappassesment.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.technocraze.newsappassesment.model.Article

interface NewsRepository {

  fun getNews(): LiveData<PagingData<Article>>

}