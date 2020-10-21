package com.alexeymerov.githubrepositories.presentation.base

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexeymerov.githubrepositories.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity : AppCompatActivity(), CoroutineScope {

	private val mainJob = Job()
	override val coroutineContext: CoroutineContext
		get() = Dispatchers.IO + mainJob

	protected fun initToolbar(titleText: String? = null, enableHomeButton: Boolean = false,
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
		cancel()
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

	inline fun <reified T : ViewModel> getViewModel(factory: ViewModelProvider.Factory): T {
		return ViewModelProvider(this, factory).get(T::class.java)
	}

}