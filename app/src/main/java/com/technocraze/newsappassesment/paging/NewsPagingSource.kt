package com.technocraze.newsappassesment.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.technocraze.newsappassesment.utils.Constants
import com.technocraze.newsappassesment.utils.Constants.PAGE_SIZE
import com.technocraze.newsappassesment.model.Article
import com.technocraze.newsappassesment.model.Status
import com.technocraze.newsappassesment.network.NewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsPagingSource(
  private val newsApiService: NewsApiService
) : PagingSource<Int, Article>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
    val position = params.key ?: 1
    return withContext(Dispatchers.IO) {
      try {
        val response = newsApiService.getLatestNews(page = position, pageSize = PAGE_SIZE)
        val prevPage: Int? = if (position == 1) null else position - 1
        val nextPage = if (position == Constants.MAX_PAGES
          || (response.status == Status.OK.value && (response.articles?.size ?: 0) < PAGE_SIZE)
        ) null else position + 1
        LoadResult.Page(
          data = response.articles!!,
          prevKey = prevPage,
          nextKey = nextPage
        )
      } catch (e: Exception) {
        LoadResult.Error(e)
      }
    }

  }

  override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
    return state.anchorPosition?.let {
      state.closestPageToPosition(it)?.prevKey?.plus(1) ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
    }
  }
}