package com.technocraze.newsappassesment

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.technocraze.newsappassesment.databinding.ActivityMainBinding
import com.technocraze.newsappassesment.fragments.ArticleClickListener
import com.technocraze.newsappassesment.fragments.ArticleDetailCallback
import com.technocraze.newsappassesment.fragments.NewsDetailFragment
import com.technocraze.newsappassesment.fragments.NewsListFragment
import com.technocraze.newsappassesment.model.Article
import com.technocraze.newsappassesment.repository.Car
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ArticleClickListener, ArticleDetailCallback {

  private lateinit var binding: ActivityMainBinding
  val TAG = "Lifecycle"
  var handler = Handler(Looper.getMainLooper())


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(TAG, "onCreate: Activity")
    var intent = Intent()
    LocalBroadcastManager.getInstance(this).sendBroadcast()
    handler.postDelayed(Runnable {
      for(i in 1..10){
        Log.d(TAG, "onCreate: $i")
      }
    },1000)

    binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
    setContentView(binding.root)
    supportFragmentManager.beginTransaction()
      .add(R.id.fragment_container, NewsListFragment())
      .addToBackStack("list")
      .commit()
    handleBackPressCallback()
  }
  suspend fun throwExcep(){
    lifecycleScope.async {
      throw Exception()
    }
  }

  suspend fun fecthDataFromNetwork1(): String {
    delay(5000)
    return "Data 1 Is Here"
  }

  suspend fun fetchDataFromNetwork2(): String {
    delay(10000)
    return "Data 2 Is Here"
  }

  suspend fun performNetworkCallWithSuspendCancellableCoroutine():
      Pair<String, String> {
    return suspendCancellableCoroutine { continuation ->
      GlobalScope.launch {
        try {
          Log.d("SUSPENDCANCELLABLE", "Inside Try Block")
          Log.d("SUSPENDCANCELLABLE", "Data1 Is Fetching")
          val data1 = fecthDataFromNetwork1()
          Log.d("SUSPENDCANCELLABLE", "Data2 Is Fetching")
          val data2 = fetchDataFromNetwork2()

          // Here we can see data1 is fetched and then it moves to
          // fetch data2.

          Log.d("SUSPENDCANCELLABLE", "Fetching Completed")

          continuation.resume(Pair(data1, data2))
        } catch (e: Throwable) {
          continuation.resumeWithException(e)
        }
      }
    }
  }

  suspend fun performNetworkCallWithAsyncAwait(): Pair<String, String> {
    return coroutineScope {
      Log.d("SUSPENDCANCELLABLE", "Inside Coroutine Block")
      Log.d("SUSPENDCANCELLABLE", "Data1 Is Fetching")
      val deferredData1 = async { fecthDataFromNetwork1() }
      Log.d("SUSPENDCANCELLABLE", "Data2 Is Fetching")
      val deferredData2 = async { fetchDataFromNetwork2() }

      // Here we can see both the network calls start fetching the data
      // parallely.

      Log.d("SUSPENDCANCELLABLE", "Before Await")

      val data1 = deferredData1.await()
      val data2 = deferredData2.await()

      Log.d("SUSPENDCANCELLABLE", "After Await")
      Log.d("SUSPENDCANCELLABLE", "Fetching Completed")

      Pair(data1, data2)
    }
  }

  override fun onStart() {
    super.onStart()
    Log.d(TAG, "onStart: Activity")
  }

  override fun onResume() {
    super.onResume()
    Log.d(TAG, "onResume: Activity")
  }

  override fun onPause() {
    super.onPause()
    Log.d(TAG, "onPause: Activity")
  }

  override fun onStop() {
    super.onStop()
    Log.d(TAG, "onStop: Activity")
  }

  override fun onDestroy() {
    super.onDestroy()
    handler.removeCallbacksAndMessages(null)
    Log.d(TAG, "onDestroy: Activity")
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