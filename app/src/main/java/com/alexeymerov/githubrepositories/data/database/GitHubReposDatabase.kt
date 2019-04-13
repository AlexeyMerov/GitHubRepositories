package com.alexeymerov.githubrepositories.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexeymerov.githubrepositories.data.database.dao.GitHubReposDAO
import com.alexeymerov.githubrepositories.data.database.entity.GHRepoDBEntity

@Database(entities = [GHRepoDBEntity::class], version = 1, exportSchema = false)
abstract class GitHubReposDatabase : RoomDatabase() {

	abstract fun getGitHubReposDao(): GitHubReposDAO

}