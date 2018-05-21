package com.raywenderlich.android.raynews.ui.topnews

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class TopNewsViewModel : ViewModel() {
  
  private val repository = TopNewsRepo()
  private val intentsSubscriber: PublishSubject<TopNewsIntent> = PublishSubject.create()
  private val privateStateObservable: Observable<TopNewsState> = composeStreams()
  val states: Observable<TopNewsState> = privateStateObservable
  
  init {
    intents().subscribe(intentsSubscriber)
  }
  
  private fun intents(): Observable<TopNewsIntent> {
    return Observable.merge(Observable.just(TopNewsIntent.LoadTopNews),null)
  }
  
  private fun composeStreams(): Observable<TopNewsState> {
    return intentsSubscriber
            //.compose(intentFilter)
            //.map({intentsToResult(it)})
            .compose(intentsToResult())
            // Cache each state and pass it to the reducer to create a new state from
            // the previous cached one and the latest Result emitted from the action processor.
            // The Scan operator is used here for the caching.
            .scan(TopNewsState.Idle, reducer)
            // When a reducer just emits previousState, there's no reason to call render. In fact,
            // redrawing the UI in cases like this can cause jank (e.g. messing up snackbar animations
            // by showing the same snackbar twice in rapid succession).
            .distinctUntilChanged()
            // Emit the last one event of the stream on subscription
            // Useful when a View rebinds to the ViewModel after rotation.
            .replay(1)
            // Create the stream on creation without waiting for anyone to subscribe
            // This allows the stream to stay alive even when the UI disconnects and
            // match the stream's lifecycle to the ViewModel's one.
            .autoConnect(0)
    
  }
  
  /*private fun intentsToResult(it: TopNewsIntent):TopNewsResult{
  
    return when(it){
      is TopNewsIntent.LoadTopNews -> {
        TopNewsResult.Success(null)
      }
      is TopNewsIntent.Idle -> {
        TopNewsResult.Failure("Hello world")
      }
      
    }
    
  }*/
  
  private fun intentsToResult() = ObservableTransformer<TopNewsIntent, TopNewsResult> { intents ->
    
    /*actions.publish { observableActions ->
      *//*Observable.merge(
              observableActions.ofType(TopNewsIntent.LoadTopNews::class.java)
                      .flatMap({repository.getEvents()}))*//*
      observableActions.flatMap({repository.fetchTopNews()
              //.toObservable()
              //.map { tasks -> LoadTasksResult.Success(tasks, action.filterType) }
              .map{news -> TopNewsResult.Success()}})
    }*/
    
    /*actions.publish { observableActions ->
      Observable.merge(
              observableActions.ofType(TopNewsIntent.LoadTopNews::class.java)
                      //.flatMap({repository.getEvents()}),
                      .compose(),
              observableActions.ofType(TopNewsIntent.Idle::class.java)
                      .flatMap()
              
              )
    }*/
    
    intents.publish { observableActions ->
      Observable.merge(
              observableActions.ofType(TopNewsIntent.LoadTopNews::class.java)
                      .flatMap({
                        repository.fetchTopNews()
                                .toObservable()
                                .map { news -> TopNewsResult.Success(news) }
                                .cast(TopNewsResult::class.java)
                                .onErrorReturn(TopNewsResult::Failure)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                      }), null)
    }
    
    /*actions.flatMap { _ ->
      repository.fetchTopNews()
              // Transform the Single to an Observable to allow emission of multiple
              // events down the stream (e.g. the InFlight event)
              .toObservable()
              // Wrap returned data into an immutable object
              .map { news -> TopNewsResult.Success(news) }
              .cast(TopNewsResult::class.java)
              // Wrap any error into an immutable object and pass it down the stream
              // without crashing.
              // Because errors are data and hence, should just be part of the stream.
              .onErrorReturn(TopNewsResult::Failure)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              // Emit an InFlight event to notify the subscribers (e.g. the UI) we are
              // doing work and waiting on a response.
              // We emit it after observing on the UI thread to allow the event to be emitted
              // on the current frame and avoid jank.
              //.startWith(TasksResult.LoadTasksResult.InFlight)
    }*/
    
    
  }
  
  
  private val reducer = BiFunction { previousState: TopNewsState, result: TopNewsResult ->
    
    when (result) {
      is TopNewsResult.Success -> {
        previousState
      }
      is TopNewsResult.Failure -> {
        previousState
      }
      
    }
    
    /*when (result) {
      is TopNewsState -> when (result) {
        is LoadTasksResult.Success -> {
          val filterType = result.filterType ?: previousState.tasksFilterType
          val tasks = filteredTasks(result.tasks, filterType)
          previousState.copy(
                  isLoading = false,
                  tasks = tasks,
                  tasksFilterType = filterType
          )
        }
        is LoadTasksResult.Failure -> previousState.copy(isLoading = false, error = result.error)
        is LoadTasksResult.InFlight -> previousState.copy(isLoading = true)
      }
      is CompleteTaskResult -> when (result) {
        is CompleteTaskResult.Success ->
          previousState.copy(
                  taskComplete = true,
                  tasks = filteredTasks(result.tasks, previousState.tasksFilterType)
          )
        is CompleteTaskResult.Failure -> previousState.copy(error = result.error)
        is CompleteTaskResult.InFlight -> previousState
        is CompleteTaskResult.HideUiNotification -> previousState.copy(taskComplete = false)
      }
      is ActivateTaskResult -> when (result) {
        is ActivateTaskResult.Success ->
          previousState.copy(
                  taskActivated = true,
                  tasks = filteredTasks(result.tasks, previousState.tasksFilterType)
          )
        is ActivateTaskResult.Failure -> previousState.copy(error = result.error)
        is ActivateTaskResult.InFlight -> previousState
        is ActivateTaskResult.HideUiNotification -> previousState.copy(taskActivated = false)
      }
      is ClearCompletedTasksResult -> when (result) {
        is ClearCompletedTasksResult.Success ->
          previousState.copy(
                  completedTasksCleared = true,
                  tasks = filteredTasks(result.tasks, previousState.tasksFilterType)
          )
        is ClearCompletedTasksResult.Failure -> previousState.copy(error = result.error)
        is ClearCompletedTasksResult.InFlight -> previousState
        is ClearCompletedTasksResult.HideUiNotification ->
          previousState.copy(completedTasksCleared = false)
      }
    }*/
  }
  
}
