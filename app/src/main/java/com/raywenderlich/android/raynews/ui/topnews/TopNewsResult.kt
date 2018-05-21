package com.raywenderlich.android.raynews.ui.topnews

import com.raywenderlich.android.raynews.model.topnews.TopNewsResponse

sealed class TopNewsResult {
  //TODO: remove the nullable
  data class Success(val result: TopNewsResponse?):TopNewsResult()
  data class Failure(val error: Throwable): TopNewsResult()
}