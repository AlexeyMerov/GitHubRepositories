package com.alexeymerov.githubrepositories.data.repository

import com.alexeymerov.githubrepositories.utils.errorLog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import java.io.IOException
import kotlin.coroutines.CoroutineContext

abstract class BaseRepository : CoroutineScope {

	private val repositoryJob = Job()

	private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
		errorLog(this::class.java.simpleName, tr = throwable)
	}

	private val coroutineName = CoroutineName(this::class.java.simpleName)

	override val coroutineContext: CoroutineContext
		get() = Dispatchers.IO + repositoryJob + exceptionHandler + coroutineName

	protected suspend fun <T> retryOnFailure(
			times: Int = 4,
			initialDelay: Long = 100, // 0.1 second
			maxDelay: Long = 1000,    // 1 second
			factor: Int = 2,
			block: suspend () -> T): T {
		var currentDelay = initialDelay
		repeat(times - 1) {
			try {
				return block()
			} catch (e: IOException) {
				errorLog(e)
			}
			delay(currentDelay)
			currentDelay = (currentDelay * factor).coerceAtMost(maxDelay)
		}
		return block() // last attempt
	}

	fun clean() = cancel()
}