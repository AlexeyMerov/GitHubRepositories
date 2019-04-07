package com.alexeymerov.githubrepositories.data.di

import com.alexeymerov.githubrepositories.data.database.dao.GitHubReposDAO
import com.alexeymerov.githubrepositories.data.repository.GitHubReposRepository
import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import com.alexeymerov.githubrepositories.data.server.communicator.GitHubCommunicator
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

	@Provides
	@RepositoryScope
	fun provideGitHubRepository(gitHubCommunicator: GitHubCommunicator, gitHubReposDAO: GitHubReposDAO)
			: IGitHubReposRepository = GitHubReposRepository(gitHubCommunicator, gitHubReposDAO)

}