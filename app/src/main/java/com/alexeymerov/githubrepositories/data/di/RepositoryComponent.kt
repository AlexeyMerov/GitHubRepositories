package com.alexeymerov.githubrepositories.data.di

import com.alexeymerov.githubrepositories.app.di.AppComponent
import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import dagger.Component

@RepositoryScope
@Component(modules = [RepositoryModule::class], dependencies = [AppComponent::class])
interface RepositoryComponent {

	fun getGitHubReposRepository(): IGitHubReposRepository

}