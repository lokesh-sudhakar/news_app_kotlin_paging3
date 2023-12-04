package com.technocraze.newsappassesment.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.technocraze.newsappassesment.utils.DateUtils
import com.technocraze.newsappassesment.R
import com.technocraze.newsappassesment.databinding.FragmentNewsDetailBinding
import com.technocraze.newsappassesment.model.Article
import com.technocraze.newsappassesment.repository.Car
import com.technocraze.newsappassesment.utils.makeHyperLink
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


interface ArticleDetailCallback {

  fun onBackClick()

}

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

  private lateinit var binding: FragmentNewsDetailBinding
  private lateinit var articleDetailCallback: ArticleDetailCallback
  private lateinit var article: Article
  @Inject
  lateinit var  car: Car
  @Inject
  lateinit var  bmw: Car


  companion object {
    const val ARG_ARTICLE = "article"
    const val TAG = "Lifecycle"

    fun getInstance(article: Article): NewsDetailFragment {
      val bundle = bundleOf().apply {
        putParcelable(ARG_ARTICLE, article)
      }
      val fragment = NewsDetailFragment().apply {
        arguments = bundle
      }
      return fragment
    }
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    Log.d(TAG, "onAttach: Detail")
    if (context is ArticleDetailCallback) {
      articleDetailCallback = context
    } else {
      throw IllegalStateException("Activity calling NewsDetailFragment Fragment needs to implement ArticleDetailCallback")
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(TAG, "onCreate: Detail")
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    Log.d(TAG, "onCreateView: Detail")
    Log.d(TAG, "NewsDetailFragment:car ${car.getName()}")
    Log.d(TAG, "NewsDetailFragment:bmw ${bmw.getName()}")
    binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
    extractDataFromArguments()
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViews()
    Log.d(TAG, "onViewCreated: Detail")
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    Log.d(TAG, "onActivityCreated: Detail")
  }

  override fun onViewStateRestored(savedInstanceState: Bundle?) {
    super.onViewStateRestored(savedInstanceState)
    Log.d(TAG, "onViewStateRestored: Detail")
  }

  override fun onStart() {
    super.onStart()
    Log.d(TAG, "onStart: Detail")
  }

  override fun onResume() {
    super.onResume()
    Log.d(TAG, "onResume: Detail")
  }

  override fun onPause() {
    super.onPause()
    Log.d(TAG, "onPause: Detail")
  }

  override fun onStop() {
    super.onStop()
    Log.d(TAG, "onStop: Detail")
  }

  override fun onDestroyView() {
    super.onDestroyView()
    Log.d(TAG, "onDestroyView: Detail")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG, "onDestroy: Detail")
  }

  override fun onDetach() {
    super.onDetach()
    Log.d(TAG, "onDetach: Detail")
  }

  private fun initViews() {
    if (::article.isInitialized) {
      binding.apply {
        contentTv.text = article.description
        if (article.source == null){
          sourceTv.text = article.source?.name
        } else {
          sourceTv.isVisible = false
        }
        authorTv.text = article.author
        publishedTv.text = DateUtils.formatData(article.publishedAt)
        backNav.setOnClickListener {
          articleDetailCallback.onBackClick()
        }
        articleTitle.text = article.title
        if (context != null) {
          openInBrowser.makeHyperLink(article.url, getString(R.string.open_in_browser), requireContext())
        } else {
          openInBrowser.visibility = View.GONE
        }
      }
      Glide.with(binding.root.context).load(article.urlToImage)
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)
        .into(binding.articlePoster);
    }
  }



  private fun extractDataFromArguments() {
    arguments?.let { args ->
      if (args.containsKey(ARG_ARTICLE)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
          article = arguments?.getParcelable(ARG_ARTICLE, Article::class.java)!!
        } else {
          article = arguments?.getParcelable(ARG_ARTICLE)!!
        }
      }
    }
  }
}

