package com.technocraze.newsappassesment.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.technocraze.newsappassesment.R
import com.technocraze.newsappassesment.databinding.NewsItemViewBinding
import com.technocraze.newsappassesment.model.Article

class NewsPaggingAdapter(val onItemClick: (Article)->Unit): PagingDataAdapter<Article, NewsPaggingAdapter.NewsViewHolder>(
  comparator,) {

  companion object{
    val comparator = object  : DiffUtil.ItemCallback<Article>() {
      override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
      }

      override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
      }
    }
  }

  override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
    val item = getItem(position)
    item?.let {
      holder.bind(it,onItemClick)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
    val binding = NewsItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return NewsViewHolder(binding)
  }

  class NewsViewHolder(private val binding: NewsItemViewBinding):
    RecyclerView.ViewHolder(binding.root){

    fun bind(article: Article, onItemClick: (Article)->Unit){
      with(binding){
        root.setOnClickListener {
          onItemClick(article)
        }
        titleTv.text = article.title
      }
      Glide.with(binding.root.context).load(article.urlToImage)
        .placeholder(R.drawable.placeholder_image1)
        .error(R.drawable.placeholder_image1)
        .centerCrop()
        .into(binding.image);
    }

  }
}