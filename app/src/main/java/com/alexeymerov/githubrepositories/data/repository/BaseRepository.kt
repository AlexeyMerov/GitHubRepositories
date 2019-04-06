package com.alexeymerov.githubrepositories.data.repository

import io.reactivex.SingleTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

abstract class BaseRepository {

	companion object {
		private const val DEFAULT_TIMEOUT = 10L
		private const val DEFAULT_RETRY_ATTEMPTS: Long = 4
	}

	private val compositeDisposable: CompositeDisposable = CompositeDisposable()

	protected fun <T> singleTransformer(): SingleTransformer<T, T> = SingleTransformer {
		it
			.subscribeOn(Schedulers.io())
			.observeOn(Schedulers.io())
			.timeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
			.retry(DEFAULT_RETRY_ATTEMPTS)
	}

	private fun Disposable.addDisposable() {
		compositeDisposable.add(this)
	}

	fun clean() = compositeDisposable.clear()
}