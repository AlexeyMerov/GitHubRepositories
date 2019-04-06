package com.alexeymerov.githubrepositories

import android.app.Application
import com.alexeymerov.githubrepositories.di.component.DaggerAppComponent
import com.alexeymerov.githubrepositories.di.component.DaggerViewModelComponent
import com.alexeymerov.githubrepositories.di.component.ViewModelComponent
import com.alexeymerov.githubrepositories.di.module.BaseApiModule
import com.alexeymerov.githubrepositories.di.module.DaoModule
import com.alexeymerov.githubrepositories.di.module.DataBaseModule
import com.alexeymerov.githubrepositories.di.module.GitHubApiModule
import com.alexeymerov.githubrepositories.di.module.UseCaseModule

class App : Application() {

	companion object {
		lateinit var viewModelComponent: ViewModelComponent
	}

	override fun onCreate() {
		super.onCreate()
		initDagger()
	}

	private fun initDagger() {
		val appComponent = DaggerAppComponent.builder().applicationContext(this).build()
		viewModelComponent = DaggerViewModelComponent.builder()
			.appComponent(appComponent)
			.dataBaseModule(DataBaseModule()).daoModule(DaoModule())
			.baseApiModule(BaseApiModule()).gitHubApiModule(GitHubApiModule())
			.useCaseModule(UseCaseModule())
			.build()
	}
}