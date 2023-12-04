package com.technocraze.newsappassesment.repository

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.technocraze.newsappassesment.network.NewsApiService
import com.technocraze.newsappassesment.paging.NewsPagingSource
import com.technocraze.newsappassesment.utils.PagingConstant.PAGE_SIZE
import com.technocraze.newsappassesment.utils.PagingConstant.PREFETCH_DISTANCE

import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor() : NewsRepository {

  @Inject
  lateinit var newsApiService: NewsApiService
  var publishSubject = PublishSubject.create<Int>()
  var replaySubject = ReplaySubject.create<Int>()
  var behaviorSubject = BehaviorSubject.create<Int>()
  var asycnSubject = AsyncSubject.create<Int>()
  var handler  = Handler(Looper.getMainLooper())

  var liveData = MutableLiveData<String>()

  override fun getNews(category: String) = Pager(
    config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_DISTANCE),
    pagingSourceFactory = { NewsPagingSource(newsApiService,category) }
  ).liveData

  suspend fun uploadImage(file: File) {
    newsApiService.uploadImage(
      image = MultipartBody.Part.createFormData(
        name = "image",
        filename = file.name,
        body = file.asRequestBody()
      )
    )
  }

  override suspend fun getFiles(times: Int): String {
    liveData.postValue("dkcj")

    //
    // publishSubject.onNext(1)
    // publishSubject.onNext(2)
    // publishSubject.onNext(4)
    // publishSubject.subscribe{
    //   println(it)
    // }
    //
    // Observable.zip(Observable.just(newsApiService.fetResults()),Observable.just(newsApiService.fetchData()), BiFunction{ t1,t2->
    //    2
    // }).subscribeOn(Schedulers.io())
    //   .observeOn(AndroidSchedulers.mainThread())
    //   .subscribe {  }
    //
    // var disposable = Observable.just(newsApiService.fetResults())
    //   .flatMap {  res->
    //      Observable.just(newsApiService.fetchData())
    //   }.map { it.toString() }
    //   .subscribeOn(Schedulers.io())
    //   .observeOn(AndroidSchedulers.mainThread())
    //   .subscribe({ item ->
    //     print(item)
    //   }, { t ->
    //     println(t)
    //   }, {
    //
    //   })
    // disposable.dispose()
    return ""
  }

}