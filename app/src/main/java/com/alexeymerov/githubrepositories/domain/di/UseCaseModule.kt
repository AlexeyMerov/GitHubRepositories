package com.alexeymerov.githubrepositories.domain.di

import com.alexeymerov.githubrepositories.domain.usecase.ReposUseCase
import com.alexeymerov.githubrepositories.domain.usecase.contract.IReposUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

	@Binds
	@ViewModelScoped
	abstract fun provideReposUseCase(reposUseCase: ReposUseCase): IReposUseCase

}