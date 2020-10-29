package com.alexeymerov.githubrepositories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.alexeymerov.githubrepositories.utils.errorLog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

	private val viewModelJob = Job()

	private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
		errorLog("BaseViewModel", tr = throwable)
	}

	override val coroutineContext: CoroutineContext
		get() = Dispatchers.IO + viewModelJob + exceptionHandler

	override fun onCleared() {
		cancel()
		super.onCleared()
	}

}