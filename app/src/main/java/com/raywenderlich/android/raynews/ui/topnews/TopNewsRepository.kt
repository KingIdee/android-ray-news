package com.raywenderlich.android.raynews.ui.topnews

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.raywenderlich.android.raynews.BuildConfig
import com.raywenderlich.android.raynews.api.NewsApi
import com.raywenderlich.android.raynews.model.topnews.TopNewsResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class TopNewsRepository {

  fun fetchTopNews(): Single<TopNewsResponse> {
    return getRetrofitClient().getTopNews("us","business",BuildConfig.API_KEY)
  }

  private fun getRetrofitClient(): NewsApi {

    val gson = GsonBuilder().setLenient().create()
    val builder = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))


    //setup logging interceptor
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor.Level.BODY
    } else {
      HttpLoggingInterceptor.Level.NONE
    }

    // setup httpclient
    val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            //.addInterceptor(connectionInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

    val retrofit = builder
            .client(httpClient)
            .build()
    return retrofit.create(NewsApi::class.java)
  }

}