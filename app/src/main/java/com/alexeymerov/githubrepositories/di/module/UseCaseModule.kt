package com.alexeymerov.githubrepositories.di.module

import com.alexeymerov.githubrepositories.data.repository.GitHubReposRepository
import com.alexeymerov.githubrepositories.domain.usecase.ReposUseCase
import com.alexeymerov.githubrepositories.domain.usecase.contract.IReposUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryModule::class])
class UseCaseModule {

	@Provides
	fun provideReposUseCase(gitHubReposRepository: GitHubReposRepository): IReposUseCase =
		ReposUseCase(gitHubReposRepository)

}