package com.technocraze.newsappassesment.repository

import kotlin.coroutines.suspendCoroutine

class ConvertCalbacktoCoroutine {

  fun apiCall(onrespone:(String)->Unit){
    var i =1
    if (i==0){
      onrespone("cool")
    } else {
      onrespone("Acmkd")
    }
  }

  suspend fun apiCall():String{
    return suspendCoroutine { cont->
      var i =1
      if (i==0){
        cont.resumeWith(Result.success("dfvd"))
        // onrespone("cool")
      } else {
        cont.resumeWith(Result.success("cdjsdjc"))
      }
    }

  }

}