package com.example.notesproject.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseViewModel : ViewModel() {
	private val disposables = CompositeDisposable()

	private fun Disposable.addToDisposables() = disposables.add(this)


	fun <T> Single<T>.subscribeIoObserveMain(
		successCallback: Consumer<T>,
		errorCallback: Consumer<Throwable>
	) = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
		successCallback, errorCallback
	).addToDisposables()

	fun <T> Observable<T>.subscribeIoObserveMain(
		successCallback: Consumer<T>,
		errorCallback: Consumer<Throwable>
	) = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
		successCallback, errorCallback
	).addToDisposables()

	fun Completable.subscribeIoObserveMain(
		successCallback: Action,
		errorCallback: Consumer<Throwable>
	) = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
		successCallback, errorCallback
	).addToDisposables()

	override fun onCleared() {
		disposables.dispose()
		super.onCleared()
	}
}