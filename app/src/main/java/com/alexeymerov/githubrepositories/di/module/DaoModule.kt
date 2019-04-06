package com.alexeymerov.githubrepositories.di.module

import com.alexeymerov.githubrepositories.data.database.GitHubReposDatabase
import dagger.Module
import dagger.Provides

@Module(includes = [DataBaseModule::class])
class DaoModule {

	@Provides
	fun provideGitHubReposDao(gitHubReposDatabase: GitHubReposDatabase) = gitHubReposDatabase.getGitHubReposDao()

}