package com.alexeymerov.githubrepositories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

	private var viewModelJob = Job()

	override val coroutineContext: CoroutineContext
		get() = Dispatchers.IO + viewModelJob

	override fun onCleared() {
		cancel()
		super.onCleared()
	}

}