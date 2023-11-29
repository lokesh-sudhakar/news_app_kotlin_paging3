package com.technocraze.newsappassesment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.technocraze.newsappassesment.databinding.ActivityMainBinding
import com.technocraze.newsappassesment.fragments.ArticleClickListener
import com.technocraze.newsappassesment.fragments.ArticleDetailCallback
import com.technocraze.newsappassesment.fragments.NewsDetailFragment
import com.technocraze.newsappassesment.fragments.NewsListFragment
import com.technocraze.newsappassesment.model.Article
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ArticleClickListener, ArticleDetailCallback {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
    setContentView(binding.root)
    supportFragmentManager.beginTransaction()
      .add(R.id.fragment_container, NewsListFragment())
      .addToBackStack("list")
      .commit()
    handleBackPressCallback()
  }

  private fun handleBackPressCallback() {
    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
          finish()
        } else {
          supportFragmentManager.popBackStack()
        }
      }
    })
  }

  override fun onArticleClick(article: Article) {
    supportFragmentManager.beginTransaction()
      .replace(R.id.fragment_container, NewsDetailFragment.getInstance(article))
      .addToBackStack("detail")
      .commit()
  }

  override fun onBackClick() {
    onBackPressedDispatcher.onBackPressed()
  }
}