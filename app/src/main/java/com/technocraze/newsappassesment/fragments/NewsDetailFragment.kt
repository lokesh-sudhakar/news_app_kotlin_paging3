package com.technocraze.newsappassesment.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
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
import com.technocraze.newsappassesment.utils.makeHyperLink


interface ArticleDetailCallback {

  fun onBackClick()

}


class NewsDetailFragment : Fragment() {

  private lateinit var binding: FragmentNewsDetailBinding
  private lateinit var articleDetailCallback: ArticleDetailCallback
  private lateinit var article: Article


  companion object {
    const val ARG_ARTICLE = "article"

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
    if (context is ArticleDetailCallback) {
      articleDetailCallback = context
    } else {
      throw IllegalStateException("Activity calling NewsDetailFragment Fragment needs to implement ArticleDetailCallback")
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
    extractDataFromArguments()
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViews()
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

