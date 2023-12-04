package com.technocraze.newsappassesment.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.technocraze.newsappassesment.databinding.ProgressItemViewBinding

class LoaderAdapter(val retry: ()->Unit): LoadStateAdapter<LoaderAdapter.LoadViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
    val binding = ProgressItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return LoadViewHolder(binding)
  }

  override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
    holder.bind(loadState)
  }

  inner class LoadViewHolder(val binding: ProgressItemViewBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(loadState: LoadState){
      when(loadState){
        is LoadState.Loading->{
          binding.progressBar.isVisible = true
          binding.retryBtn.isVisible = false
        }
        is LoadState.Error ->{
          binding.progressBar.isVisible = false
          binding.retryBtn.isVisible = true
        }
        is LoadState.NotLoading-> {
          binding.progressBar.isVisible = false
          binding.retryBtn.isVisible = false
        }

      }
      binding.retryBtn.setOnClickListener {
        retry()
      }
    }
  }

}