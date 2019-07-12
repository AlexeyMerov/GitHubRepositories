package com.alexeymerov.githubrepositories.app

import android.app.Application
import com.alexeymerov.githubrepositories.app.di.AppComponent
import com.alexeymerov.githubrepositories.app.di.DaggerAppComponent
import com.alexeymerov.githubrepositories.data.di.DaggerRepositoryComponent
import com.alexeymerov.githubrepositories.data.di.RepositoryComponent
import com.alexeymerov.githubrepositories.data.di.RepositoryModule
import com.alexeymerov.githubrepositories.domain.di.DaggerUseCaseComponent
import com.alexeymerov.githubrepositories.domain.di.UseCaseComponent
import com.alexeymerov.githubrepositories.domain.di.UseCaseModule
import com.alexeymerov.githubrepositories.presentation.di.DaggerViewModelComponent
import com.alexeymerov.githubrepositories.presentation.di.ViewModelComponent

object DaggerApp {

	lateinit var viewModelComponent: ViewModelComponent
		private set

	fun init(application: Application) {
		val appComponent = initAppComponent(application)
		val repositoryComponent = initRepositoryComponent(appComponent)
		val useCaseComponent = initUseCaseComponent(repositoryComponent)

		viewModelComponent = initViewModelComponent(useCaseComponent)
	}

	private fun initViewModelComponent(useCaseComponent: UseCaseComponent) =
		DaggerViewModelComponent.builder().useCaseComponent(useCaseComponent).build()

	private fun initUseCaseComponent(repositoryComponent: RepositoryComponent) =
		DaggerUseCaseComponent.builder()
				.repositoryComponent(repositoryComponent)
				.useCaseModule(UseCaseModule())
				.build()

	private fun initRepositoryComponent(appComponent: AppComponent) =
		DaggerRepositoryComponent.builder()
				.appComponent(appComponent)
				.repositoryModule(RepositoryModule())
				.build()

	private fun initAppComponent(application: Application) =
		DaggerAppComponent.factory().create(application)

}