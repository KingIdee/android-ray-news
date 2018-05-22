package com.raywenderlich.android.raynews.ui.topnews

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class TopNewsViewModel : ViewModel() {

  private val repository = TopNewsRepository()
  private val intentsSubscriber: PublishSubject<TopNewsIntent> = PublishSubject.create()
  private val privateStateObservable: Observable<TopNewsState> = composeStreams()
  val states: Observable<TopNewsState> = privateStateObservable

  fun subscribeToIntents() {
    intents().subscribe(intentsSubscriber)
  }

  private fun intents(): Observable<TopNewsIntent> {
    return Observable.just(TopNewsIntent.LoadTopNews)
  }

  private fun composeStreams(): Observable<TopNewsState> {
    return intentsSubscriber
            .compose(intentToResult())
            .scan(TopNewsState.idle(), reducer)
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0)
  }

  private fun intentToResult() = ObservableTransformer<TopNewsIntent, TopNewsResult> { intents ->

    intents.publish { observableActions ->
      observableActions.ofType(TopNewsIntent.LoadTopNews::class.java)
              .flatMap({
                repository.fetchTopNews()
                        .toObservable()
                        .map { news -> TopNewsResult.Success(news) }
                        .cast(TopNewsResult::class.java)
                        .onErrorReturn(TopNewsResult::Failure)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
              })
    }

  }

  companion object {
    private val reducer = BiFunction { previousState: TopNewsState, result: TopNewsResult ->
      when (result) {
        is TopNewsResult.Success -> {
          previousState.copy(
                  loading = false,
                  response = result.result,
                  error = null
          )
        }
        is TopNewsResult.Failure -> {
          previousState.copy(
                  loading = false,
                  response = null,
                  error = result.error
          )
        }

      }

    }
  }

}
