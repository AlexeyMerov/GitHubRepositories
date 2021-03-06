package com.alexeymerov.githubrepositories.presentation.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.alexeymerov.githubrepositories.R
import com.alexeymerov.githubrepositories.utils.errorLog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity(), CoroutineScope {

	private val mainJob = SupervisorJob()

	private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
		errorLog(this::class.java.simpleName, tr = throwable)
	}

	private val coroutineName = CoroutineName(this::class.java.simpleName)

	override val coroutineContext: CoroutineContext
		get() = Dispatchers.IO + mainJob + exceptionHandler + coroutineName

	protected lateinit var binding: T

	abstract fun inflateViewBinding(): T

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = inflateViewBinding()
		setContentView(binding.root)
	}

	protected fun initToolbar(titleText: String? = null,
							  enableHomeButton: Boolean = false,
							  @DrawableRes iconRes: Int? = null) {
		findViewById<Toolbar>(R.id.toolbar)?.apply {
			setSupportActionBar(this)
			setNavigationOnClickListener { onBackPressed() }
			supportActionBar?.apply {
				title = titleText
				setDisplayHomeAsUpEnabled(enableHomeButton)
				iconRes?.let { setNavigationIcon(iconRes) }
			}
		}
	}

	protected fun toggleToolbarVisibility() {
		supportActionBar?.apply {
			if (isShowing) hide() else show()
		}
	}

	override fun onDestroy() {
		coroutineContext.cancel()
		super.onDestroy()
	}

	override fun startActivity(intent: Intent) {
		super.startActivity(intent)
		overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
	}

	override fun finish() {
		super.finish()
		overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
	}

}