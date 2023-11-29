package com.technocraze.newsappassesment.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technocraze.newsappassesment.databinding.FragmentNewsListBinding
import com.technocraze.newsappassesment.model.Article
import com.technocraze.newsappassesment.paging.LoaderAdapter
import com.technocraze.newsappassesment.paging.NewsPaggingAdapter
import com.technocraze.newsappassesment.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

interface ArticleClickListener {

  fun onArticleClick(article: Article)

}

@AndroidEntryPoint
class NewsListFragment : Fragment() {

  private lateinit var binding: FragmentNewsListBinding
  private val viewModel: NewsViewModel by viewModels()
  private lateinit var newsListAdapter: NewsPaggingAdapter
  private lateinit var articleClickListener: ArticleClickListener

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is ArticleClickListener) {
      articleClickListener = context
    } else {
      throw IllegalStateException("Activity calling NewsList Fragment needs to implement ArticleClickListener")
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentNewsListBinding.inflate(inflater, container, false)
    initView()
    return binding.root
  }

  private fun initView() {
    setupNewsRv()
    handleLoadState()
    handleRetryClick()
    observePagingLiveData()
  }

  private fun observePagingLiveData() {
    viewModel.article.observe(viewLifecycleOwner) { articleList ->
      newsListAdapter.submitData(lifecycle, articleList)
    }
  }

  private fun handleRetryClick() {
    binding.retryBtn.setOnClickListener {
      newsListAdapter.retry()
    }
  }

  private fun handleLoadState() {
    lifecycleScope.launch(Dispatchers.Main) {
      newsListAdapter.loadStateFlow.collectLatest { loadStates ->
        val recyclerViewHeight = binding.newsRv.measuredHeight
        when (loadStates.refresh) {
          is LoadState.Loading -> {
            if (recyclerViewHeight == 0) {
              // showing loading only when recycler view has no items
              binding.progressBar.isVisible = true
              binding.errorLayout.isVisible = false
              binding.newsRv.isVisible = false
            }
          }

          is LoadState.Error -> {
            if (recyclerViewHeight == 0) {
              // showing error only when recycler view has no items
              binding.newsRv.isVisible = true
              binding.errorLayout.isVisible = true
              binding.progressBar.isVisible = false
            }
          }

          is LoadState.NotLoading -> {
            binding.progressBar.isVisible = false
            binding.errorLayout.isVisible = false
            binding.newsRv.isVisible = true
          }
        }
      }
    }
  }

  private fun setupNewsRv() {
    newsListAdapter = NewsPaggingAdapter {
      articleClickListener.onArticleClick(it)
    }
    with(binding.newsRv) {
      layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      hasFixedSize()
      adapter = newsListAdapter
      adapter = newsListAdapter.withLoadStateHeaderAndFooter(
        header = LoaderAdapter(),
        footer = LoaderAdapter()
      )
    }
  }

}