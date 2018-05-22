package com.raywenderlich.android.raynews.ui.topnews

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.raywenderlich.android.raynews.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class TopNewsFragment : Fragment() {

  private val disposable = CompositeDisposable()
  private val newsAdapter = TopNewsAdapter(ArrayList())
  lateinit var progressBar: ProgressBar

  companion object {
    fun newInstance() = TopNewsFragment()
  }

  private lateinit var viewModel: TopNewsViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.top_news_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
    recyclerView.layoutManager = LinearLayoutManager(activity)
    recyclerView.adapter = newsAdapter
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    progressBar = view!!.findViewById(R.id.progress_bar)
    viewModel = ViewModelProviders.of(this).get(TopNewsViewModel::class.java)
    disposable.add(viewModel.states
            .subscribe({ render(it) }))
    viewModel.subscribeToIntents()
  }

  private fun render(it: TopNewsState?) {
    if (it!!.response != null) {
      newsAdapter.setItems(it.response!!.articles)
    }
    if (!it.loading) {
      progressBar.visibility = View.INVISIBLE
    } else {
      progressBar.visibility = View.VISIBLE
    }

    if (it.error != null) {
      Toast.makeText(activity, it.error.toString(), Toast.LENGTH_LONG).show()
    }

  }

}
