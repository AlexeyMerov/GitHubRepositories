package com.alexeymerov.githubrepositories.di.module

import com.alexeymerov.githubrepositories.data.database.dao.GitHubReposDAO
import com.alexeymerov.githubrepositories.data.repository.GitHubReposRepository
import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import com.alexeymerov.githubrepositories.data.server.communicator.GitHubCommunicator
import dagger.Module
import dagger.Provides

@Module(includes = [GitHubApiModule::class, DaoModule::class])
class RepositoryModule {

	@Provides
	fun provideGitHubRepository(gitHubCommunicator: GitHubCommunicator,
								gitHubReposDAO: GitHubReposDAO): IGitHubReposRepository =
		GitHubReposRepository(gitHubCommunicator, gitHubReposDAO)

}