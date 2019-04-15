package com.alexeymerov.githubrepositories.domain.di

import com.alexeymerov.githubrepositories.data.di.RepositoryComponent
import com.alexeymerov.githubrepositories.domain.usecase.contract.IReposUseCase
import dagger.Component

@UseCaseScope
@Component(modules = [UseCaseModule::class], dependencies = [RepositoryComponent::class])
interface UseCaseComponent {

	fun getReposUseCase(): IReposUseCase

}