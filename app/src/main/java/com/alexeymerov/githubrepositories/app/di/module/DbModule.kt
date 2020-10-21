package com.alexeymerov.githubrepositories.app.di.module

import android.app.Application
import androidx.room.Room
import com.alexeymerov.githubrepositories.data.database.GitHubReposDatabase
import com.alexeymerov.githubrepositories.data.database.dao.GitHubReposDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DbModule {

	@Provides
	@Singleton
	fun provideGitHubReposDatabase(context: Application) =
		Room.databaseBuilder(context, GitHubReposDatabase::class.java, "github_repos_database")
				.fallbackToDestructiveMigration()
				.build()

	@Provides
	@Singleton
	fun provideGitHubReposDao(gitHubReposDatabase: GitHubReposDatabase): GitHubReposDAO =
		gitHubReposDatabase.getGitHubReposDao()

}
