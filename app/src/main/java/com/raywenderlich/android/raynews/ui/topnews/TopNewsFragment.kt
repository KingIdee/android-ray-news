package com.raywenderlich.android.raynews.ui.topnews

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raywenderlich.android.raynews.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class TopNewsFragment : Fragment() {
  
  private val disposable = CompositeDisposable()
  
  companion object {
    fun newInstance() = TopNewsFragment()
  }
  
  private lateinit var viewModel: TopNewsViewModel
  
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.top_news_fragment, container, false)
  }
  
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(TopNewsViewModel::class.java)
    disposable.add(viewModel.states
            .subscribe({ render(it)}))
  }
  
  private fun render(it: TopNewsState?) {
    
  }
  
}
