package com.alexeymerov.githubrepositories.app.di

import android.app.Application
import com.alexeymerov.githubrepositories.app.di.module.DbModule
import com.alexeymerov.githubrepositories.app.di.module.GitHubApiModule
import com.alexeymerov.githubrepositories.data.database.dao.GitHubReposDAO
import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DbModule::class, GitHubApiModule::class])
interface AppComponent {

	@Component.Builder
	interface Builder {

		@BindsInstance
		fun applicationContext(application: Application): Builder

		fun build(): AppComponent

	}

	fun getApplication(): Application

	fun getGitHubApiService(): GitHubApiService

	fun getGitHubReposDAO(): GitHubReposDAO

}
