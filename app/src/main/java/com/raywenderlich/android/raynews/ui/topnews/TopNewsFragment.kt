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
    viewModel.subscribeToIntents(intents())
  }

  override fun onDestroy() {
    super.onDestroy()
    disposable.dispose()
  }

  private fun intents(): Observable<TopNewsIntent> {
    return Observable.just(TopNewsIntent.LoadTopNews)
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
