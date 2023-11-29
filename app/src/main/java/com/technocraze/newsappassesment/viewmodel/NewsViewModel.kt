package com.technocraze.newsappassesment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.technocraze.newsappassesment.model.Article
import com.technocraze.newsappassesment.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

  val article: LiveData<PagingData<Article>> = repository.getNews().cachedIn(viewModelScope)


}