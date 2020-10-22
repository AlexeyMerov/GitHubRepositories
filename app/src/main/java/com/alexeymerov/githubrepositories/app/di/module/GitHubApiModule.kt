package com.alexeymerov.githubrepositories.app.di.module

import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService
import com.alexeymerov.githubrepositories.data.server.communicator.GitHubCommunicator
import com.alexeymerov.githubrepositories.data.server.communicator.contract.IGitHubCommunicator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module(includes = [BaseApiModule::class])
object GitHubApiModule {

	private val API_URL = "https://api.github.com/"

	@Provides
	@Singleton
	fun provideApiUrl() = API_URL

	@Provides
	@Singleton
	fun provideApiServiceClass() = GitHubApiService::class.java

	@Provides
	@Singleton
	fun provideApiService(retrofit: Retrofit, serviceClass: Class<GitHubApiService>): GitHubApiService =
		retrofit.create(serviceClass)

	@Provides
	@Singleton
	fun provideGitHubCommunicator(apiService: GitHubApiService): IGitHubCommunicator = GitHubCommunicator(apiService)

}