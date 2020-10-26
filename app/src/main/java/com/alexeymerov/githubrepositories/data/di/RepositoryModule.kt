package com.alexeymerov.githubrepositories.data.di

import com.alexeymerov.githubrepositories.data.repository.GitHubReposRepository
import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

	@Binds
	@ActivityRetainedScoped
	abstract fun provideGitHubRepository(gitHubReposRepository: GitHubReposRepository): IGitHubReposRepository

}