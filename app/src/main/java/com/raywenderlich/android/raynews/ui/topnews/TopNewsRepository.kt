/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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