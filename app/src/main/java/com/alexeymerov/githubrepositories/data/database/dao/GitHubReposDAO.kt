package com.alexeymerov.githubrepositories.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.alexeymerov.githubrepositories.data.database.entity.GitHubRepoEntity

@Dao
abstract class GitHubReposDAO : BaseDAO<GitHubRepoEntity>() {

	companion object {
		const val TABLE_NAME: String = "repo_entity"
		const val NAME_ROW: String = "name"
	}

	@Query("SELECT * FROM $TABLE_NAME ORDER BY $NAME_ROW ASC")
	abstract fun getAll(): List<GitHubRepoEntity>

	@Query("SELECT * FROM $TABLE_NAME ORDER BY $NAME_ROW ASC")
	abstract fun getAllLive(): LiveData<List<GitHubRepoEntity>>

	@Query("DELETE FROM $TABLE_NAME")
	abstract fun removeAll()

}