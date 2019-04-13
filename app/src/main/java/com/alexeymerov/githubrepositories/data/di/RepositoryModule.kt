package com.alexeymerov.githubrepositories.data.di

import com.alexeymerov.githubrepositories.data.database.dao.GitHubReposDAO
import com.alexeymerov.githubrepositories.data.mapper.ResponseMapper
import com.alexeymerov.githubrepositories.data.repository.GitHubReposRepository
import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import com.alexeymerov.githubrepositories.data.server.communicator.contract.IGitHubCommunicator
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

	@Provides
	@RepositoryScope
	fun provideReposMapper() = ResponseMapper()

	@Provides
	@RepositoryScope
	fun provideGitHubRepository(gitHubCommunicator: IGitHubCommunicator,
								gitHubReposDAO: GitHubReposDAO,
								reposMapper: ResponseMapper): IGitHubReposRepository =
		GitHubReposRepository(gitHubCommunicator, gitHubReposDAO, reposMapper)

}