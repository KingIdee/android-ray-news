package com.raywenderlich.android.raynews

import com.raywenderlich.android.raynews.model.topnews.TopNewsResponse
import io.reactivex.Single
import retrofit2.http.GET

interface NewsApi {

  @GET("/v2/top-headlines?country=us&category=business&apiKey=bb794aaa47b84a7685fc628188938680")
  fun getTopNews(): Single<TopNewsResponse>
  
}