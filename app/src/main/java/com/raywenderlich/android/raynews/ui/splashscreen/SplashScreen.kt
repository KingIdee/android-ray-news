package com.raywenderlich.android.raynews.ui.splashscreen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.raywenderlich.android.raynews.ui.topnews.TopNewsActivity

class SplashScreen : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val intent = Intent(this, TopNewsActivity::class.java)
    startActivity(intent)
    finish()
  }
}
