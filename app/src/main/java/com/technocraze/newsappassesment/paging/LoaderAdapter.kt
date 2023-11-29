package com.technocraze.newsappassesment.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.technocraze.newsappassesment.databinding.ProgressItemViewBinding

class LoaderAdapter: LoadStateAdapter<LoaderAdapter.LoadViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
    val binding = ProgressItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return LoadViewHolder(binding)
  }

  override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
    holder.bind(loadState)
  }

  class LoadViewHolder(val binding: ProgressItemViewBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(loadState: LoadState){
      binding.progressBar.isVisible = loadState is LoadState.Loading
    }
  }

}