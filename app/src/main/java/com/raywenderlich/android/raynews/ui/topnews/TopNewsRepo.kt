package com.raywenderlich.android.raynews.ui.topnews

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.raywenderlich.android.raynews.NewsApi
import com.raywenderlich.android.raynews.model.topnews.TopNewsResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class TopNewsRepo {
  
  fun fetchTopNews(): Single<TopNewsResponse> {
    return getRetrofitClient().getTopNews()
  }
  
  
  private fun getRetrofitClient():NewsApi{
  
    val gson = GsonBuilder().setLenient().create()
    val builder = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
  
    // setup httpclient
    val httpClient = OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
  
    val retrofit = builder
            .client(httpClient)
            .build()
    return retrofit.create(NewsApi::class.java)
  }
  
}