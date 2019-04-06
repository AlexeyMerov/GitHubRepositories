package com.alexeymerov.githubrepositories.di.module

import android.app.Application
import androidx.room.Room
import com.alexeymerov.githubrepositories.data.database.GitHubReposDatabase
import dagger.Module
import dagger.Provides

@Module
class DataBaseModule {

	@Provides
	fun provideGitHubReposDatabase(context: Application) =
		Room.databaseBuilder(context, GitHubReposDatabase::class.java, "github_repos_database")
			.fallbackToDestructiveMigration()
			.build()

}