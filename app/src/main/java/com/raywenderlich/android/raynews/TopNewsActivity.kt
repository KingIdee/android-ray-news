package com.raywenderlich.android.raynews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.raywenderlich.android.raynews.ui.topnews.TopNewsFragment

class TopNewsActivity : AppCompatActivity() {
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.top_news_activity)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
              .replace(R.id.container, TopNewsFragment.newInstance())
              .commitNow()
    }
  }
  
}
