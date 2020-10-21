package com.alexeymerov.githubrepositories.domain.di

import com.alexeymerov.githubrepositories.domain.usecase.ReposUseCase
import com.alexeymerov.githubrepositories.domain.usecase.contract.IReposUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class UseCaseModule {

	@Binds
	@ActivityRetainedScoped
	abstract fun provideReposUseCase(reposUseCase: ReposUseCase): IReposUseCase

}