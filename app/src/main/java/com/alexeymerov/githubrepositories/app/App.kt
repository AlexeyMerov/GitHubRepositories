package com.alexeymerov.githubrepositories.app

import android.app.Application
import com.alexeymerov.githubrepositories.data.di.RepositoryModule
import com.alexeymerov.githubrepositories.di.data.component.DaggerRepositoryComponent
import com.alexeymerov.githubrepositories.di.domain.DaggerUseCaseComponent
import com.alexeymerov.githubrepositories.di.global.DaggerAppComponent
import com.alexeymerov.githubrepositories.di.presentation.DaggerViewModelComponent
import com.alexeymerov.githubrepositories.domain.di.UseCaseModule
import com.alexeymerov.githubrepositories.presentation.di.ViewModelComponent

class App : Application() {

	companion object {
		lateinit var viewModelComponent: ViewModelComponent
			private set
	}

	override fun onCreate() {
		super.onCreate()
		initDagger()
	}

	private fun initDagger() {
		val appComponent = DaggerAppComponent.builder().applicationContext(this).build()

		val repositoryComponent = DaggerRepositoryComponent.builder()
			.appComponent(appComponent)
			.repositoryModule(RepositoryModule())
			.build()

		val useCaseComponent = DaggerUseCaseComponent.builder()
			.repositoryComponent(repositoryComponent)
			.useCaseModule(UseCaseModule())
			.build()

		viewModelComponent = DaggerViewModelComponent.builder().useCaseComponent(useCaseComponent).build()
	}
}