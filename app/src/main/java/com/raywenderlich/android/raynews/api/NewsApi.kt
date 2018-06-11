package com.raywenderlich.android.raynews.api

import com.raywenderlich.android.raynews.model.topnews.TopNewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

  @GET("/v2/top-headlines")
  fun getTopNews(@Query("country") country:String,
                 @Query("category") category:String,
                 @Query("apiKey") apiKey:String): Single<TopNewsResponse>
}