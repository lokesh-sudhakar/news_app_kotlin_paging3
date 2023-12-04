package com.technocraze.newsappassesment

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import org.junit.Test

import org.junit.Assert.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() = runBlocking { // this: CoroutineScope
    launch {
      delay(200L)
      println("Task from runBlocking ${Thread.currentThread()}")
    }

    coroutineScope { // Creates a new coroutine scope
      launch {
        delay(500L)
        println("Task from nested launch ${Thread.currentThread()}")
      }

      delay(100L)
      println("Task from coroutine scope ${Thread.currentThread()}") // This line will be printed before nested launch
    }

    println("Coroutine scope is over ${Thread.currentThread()}") // This line is not printed until nested launch completes
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
}