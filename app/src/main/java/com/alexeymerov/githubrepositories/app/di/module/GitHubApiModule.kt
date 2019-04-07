package com.alexeymerov.githubrepositories.app.di.module

import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService
import com.alexeymerov.githubrepositories.data.server.communicator.GitHubCommunicator
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [BaseApiModule::class])
class GitHubApiModule {

	private val API_URL = "https://jsonplaceholder.typicode.com"

	@Provides
	@Singleton
	fun provideApiUrl() = API_URL

	@Provides
	@Singleton
	fun provideApiServiceClass() = GitHubApiService::class.java

	@Provides
	@Singleton
	fun provideApiService(retrofit: Retrofit, serviceClass: Class<GitHubApiService>) = retrofit.create(serviceClass)!!

	@Provides
	@Singleton
	fun provideGitHubCommunicator(apiService: GitHubApiService) = GitHubCommunicator(apiService)

}