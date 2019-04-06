package com.alexeymerov.githubrepositories.presentation.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alexeymerov.githubrepositories.App
import com.alexeymerov.githubrepositories.R
import com.alexeymerov.githubrepositories.di.component.ViewModelComponent

abstract class BaseActivity : AppCompatActivity() {

	inline fun <reified T : ViewModel> getViewModel(factory: ViewModelProvider.Factory): T {
		return ViewModelProviders.of(this, factory).get(T::class.java)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		injectActivity(App.viewModelComponent)
	}

	abstract fun injectActivity(component: ViewModelComponent)

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

	override fun startActivity(intent: Intent) {
		super.startActivity(intent)
		overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
	}

	override fun finish() {
		super.finish()
		overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
	}

}