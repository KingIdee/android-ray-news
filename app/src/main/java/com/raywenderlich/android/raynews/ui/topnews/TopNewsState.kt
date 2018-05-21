package com.raywenderlich.android.raynews.ui.topnews

sealed class TopNewsState {
  object Idle:TopNewsState()
  object Loading:TopNewsState()
}