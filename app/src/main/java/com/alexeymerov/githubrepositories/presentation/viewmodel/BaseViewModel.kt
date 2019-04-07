package com.alexeymerov.githubrepositories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.SingleTransformer
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel : ViewModel() {

	protected fun <T> singleTransformer(): SingleTransformer<T, T> = SingleTransformer {
		it
			.subscribeOn(Schedulers.io())
			.observeOn(Schedulers.io())
	}

}