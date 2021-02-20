package com.alexeymerov.githubrepositories.data.di

import com.alexeymerov.githubrepositories.data.repository.GitHubReposRepository
import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

	@Binds
	@Singleton
	abstract fun provideGitHubRepository(gitHubReposRepository: GitHubReposRepository): IGitHubReposRepository

}