package com.technocraze.newsappassesment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.technocraze.newsappassesment.model.Article
import com.technocraze.newsappassesment.repository.NewsRepository
import com.technocraze.newsappassesment.repository.NewsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

  val article: LiveData<PagingData<Article>> = repository.getNews("")

  suspend fun <T>retry(maxTimes: Int,
                       fetch: suspend () -> T,
                       initialDelay: Long

  ): T{

      repeat(maxTimes-1){
        try{
          return fetch()
        } catch (e:Exception){
          //dkamd
        }
        delay(initialDelay)
      }
      return fetch()


      // var res = repository.getFiles(times)
      //
      // if (res == "Failed"){
      //   Log.d("NewsViewModel", "retry:  failed")
      //   retry(times+1)
      // } else {
      //   Log.d("NewsViewModel", "retry:  success")
      // }

  }










  suspend fun <T> retryIO(
    times: Int = Int.MAX_VALUE,
    initialDelay: Long = 100, // 0.1 second
    maxDelay: Long = 1000,    // 1 second
    factor: Double = 2.0,
    block: suspend () -> T): T
  {
    var currentDelay = initialDelay
    repeat(times - 1) {
      try {
        return block()
      } catch (e: IOException) {
        // you can log an error here and/or make a more finer-grained
        // analysis of the cause to see if retry is needed
      }
      delay(currentDelay)
      currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
    return block() // last attempt
  }
}