package com.alexeymerov.githubrepositories.domain.di

import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import com.alexeymerov.githubrepositories.domain.mapper.UseCaseReposMapper
import com.alexeymerov.githubrepositories.domain.usecase.ReposUseCase
import com.alexeymerov.githubrepositories.domain.usecase.contract.IReposUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

	@Provides
	@UseCaseScope
	fun provideMapper() = UseCaseReposMapper()

	@Provides
	@UseCaseScope
	fun provideReposUseCase(gitHubReposRepository: IGitHubReposRepository, useCaseReposMapper: UseCaseReposMapper)
			: IReposUseCase = ReposUseCase(gitHubReposRepository, useCaseReposMapper)

}