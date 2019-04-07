package com.alexeymerov.githubrepositories.domain.di

import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import com.alexeymerov.githubrepositories.domain.usecase.ReposUseCase
import com.alexeymerov.githubrepositories.domain.usecase.contract.IReposUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

	@Provides
	@UseCaseScope
	fun provideReposUseCase(gitHubReposRepository: IGitHubReposRepository): IReposUseCase =
		ReposUseCase(gitHubReposRepository)

}