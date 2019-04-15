package com.alexeymerov.githubrepositories.app.di

import android.app.Application
import com.alexeymerov.githubrepositories.app.di.module.DbModule
import com.alexeymerov.githubrepositories.app.di.module.GitHubApiModule
import com.alexeymerov.githubrepositories.data.database.dao.GitHubReposDAO
import com.alexeymerov.githubrepositories.data.server.communicator.contract.IGitHubCommunicator
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DbModule::class, GitHubApiModule::class])
interface AppComponent {

	@Component.Factory
	interface Builder {

		fun create(@BindsInstance application: Application): AppComponent
	}

	fun getApplication(): Application

	fun getGitHubCommunicator(): IGitHubCommunicator

	fun getGitHubReposDAO(): GitHubReposDAO

}
