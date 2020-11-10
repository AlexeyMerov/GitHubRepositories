package com.alexeymerov.githubrepositories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.alexeymerov.githubrepositories.utils.errorLog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

	private val viewModelJob = SupervisorJob()

	private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
		errorLog(this::class.java.simpleName, tr = throwable)
	}

	private val coroutineName = CoroutineName(this::class.java.simpleName)

	override val coroutineContext: CoroutineContext
		get() = Dispatchers.IO + viewModelJob + exceptionHandler + coroutineName

	override fun onCleared() {
		cancel()
		super.onCleared()
	}

}