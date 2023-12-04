package com.technocraze.newsappassesment.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/*The authenticate() method is called when the server returns 401 Unauthorized.
For calling ApiClient.userApiService.getAuthenticationToken(), we're using execute() to make it a synchronous call.
*
* */
class TokenAuthenticator : Authenticator {
  override fun authenticate(route: Route?, response: Response): Request? {
    // This is a synchronous call
    val updatedToken = getUpdatedToken()
    return response.request.newBuilder()
      .header("dsccsd", updatedToken)
      .build()
  }

  private fun getUpdatedToken(): String {
    val requestParams = HashMap<String, String>()


    // val authTokenResponse = ApiClient.userApiService.getAuthenticationToken(requestParams).execute().body()!!
    //
    // val newToken = "${authTokenResponse.tokenType} ${authTokenResponse.accessToken}"
    // SharedPreferenceUtils.saveString(Constants.PreferenceKeys.USER_ACCESS_TOKEN, newToken)
    return ""
  }
}