package com.technocraze.newsappassesment.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.technocraze.newsappassesment.utils.Constants.PAGE_SIZE
import com.technocraze.newsappassesment.utils.Constants.PREFETCH_DISTANCE
import com.technocraze.newsappassesment.network.NewsApiService
import com.technocraze.newsappassesment.paging.NewsPagingSource

class NewsRepositoryImpl(private val newsApiService: NewsApiService) : NewsRepository {

  override fun getNews() = Pager(
    config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_DISTANCE),
    pagingSourceFactory = { NewsPagingSource(newsApiService) }
  ).liveData

}