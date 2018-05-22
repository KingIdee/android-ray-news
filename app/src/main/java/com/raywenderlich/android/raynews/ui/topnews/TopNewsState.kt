package com.raywenderlich.android.raynews.ui.topnews

import com.raywenderlich.android.raynews.model.topnews.TopNewsResponse

data class TopNewsState(var loading: Boolean, var response: TopNewsResponse?, var error: Throwable?) {
  companion object {
    fun idle(): TopNewsState {
      return TopNewsState(loading = true,
              response = null,
              error = null)
    }
  }
}