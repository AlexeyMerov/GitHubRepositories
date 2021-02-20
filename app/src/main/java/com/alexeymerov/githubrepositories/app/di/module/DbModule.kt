package com.alexeymerov.githubrepositories.app.di.module

import android.content.Context
import androidx.room.Room
import com.alexeymerov.githubrepositories.data.database.GitHubReposDatabase
import com.alexeymerov.githubrepositories.data.database.dao.GitHubReposDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DbModule {

	@Provides
	@Singleton
	fun provideGitHubReposDatabase(@ApplicationContext context: Context) =
		Room.databaseBuilder(context, GitHubReposDatabase::class.java, "github_repos_database")
				.fallbackToDestructiveMigration()
				.build()

	@Provides
	@Singleton
	fun provideGitHubReposDao(gitHubReposDatabase: GitHubReposDatabase): GitHubReposDAO =
		gitHubReposDatabase.getGitHubReposDao()

}
