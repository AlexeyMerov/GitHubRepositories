package com.alexeymerov.githubrepositories.di.module

import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService
import com.alexeymerov.githubrepositories.data.server.communicator.GitHubCommunicator
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [BaseApiModule::class])
class GitHubApiModule {

	private val API_URL = "https://jsonplaceholder.typicode.com"

	@Provides
	fun provideApiUrl() = API_URL

	@Provides
	fun provideApiServiceClass() = GitHubApiService::class.java

	@Provides
	fun provideApiService(retrofit: Retrofit, serviceClass: Class<GitHubApiService>) = retrofit.create(serviceClass)

	@Provides
	fun provideGitHubCommunicator(apiService: GitHubApiService) = GitHubCommunicator(apiService)

}