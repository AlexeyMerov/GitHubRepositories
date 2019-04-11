package com.alexeymerov.githubrepositories.app

import android.app.Application
import com.alexeymerov.githubrepositories.app.di.DaggerAppComponent
import com.alexeymerov.githubrepositories.data.di.DaggerRepositoryComponent
import com.alexeymerov.githubrepositories.data.di.RepositoryModule
import com.alexeymerov.githubrepositories.domain.di.DaggerUseCaseComponent
import com.alexeymerov.githubrepositories.domain.di.UseCaseModule
import com.alexeymerov.githubrepositories.presentation.di.DaggerViewModelComponent
import com.alexeymerov.githubrepositories.presentation.di.ViewModelComponent
import com.google.firebase.FirebaseApp

class App : Application() {

	companion object {
		lateinit var viewModelComponent: ViewModelComponent
			private set
	}

	override fun onCreate() {
		super.onCreate()
		initDagger()
		FirebaseApp.initializeApp(this)
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