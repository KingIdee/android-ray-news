package com.raywenderlich.android.raynews.ui.topnews

sealed class TopNewsIntent {

  object LoadTopNews : TopNewsIntent()

}