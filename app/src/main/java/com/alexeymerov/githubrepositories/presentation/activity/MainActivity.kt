package com.alexeymerov.githubrepositories.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.alexeymerov.githubrepositories.R.layout
import com.alexeymerov.githubrepositories.di.component.ViewModelComponent
import com.alexeymerov.githubrepositories.presentation.base.BaseActivity
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel
import javax.inject.Inject

class MainActivity : BaseActivity() {

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	private val viewModel by lazy { getViewModel<IReposViewModel>(viewModelFactory) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(layout.activity_main)
		Log.d("ReposS", viewModel.getTest())
	}

	override fun injectActivity(component: ViewModelComponent) = component.injectActivity(this)
}
