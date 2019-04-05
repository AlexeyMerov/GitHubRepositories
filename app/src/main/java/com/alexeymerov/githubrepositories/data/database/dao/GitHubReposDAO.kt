package com.alexeymerov.githubrepositories.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.alexeymerov.githubrepositories.data.database.entity.GitHubRepoEntity

@Dao
interface GitHubReposDAO : BaseDAO<GitHubRepoEntity> {

	companion object {
		const val TABLE_NAME: String = "repo_entity"
		const val NAME_ROW: String = "name"
	}

	@Query("SELECT * FROM $TABLE_NAME ORDER BY $NAME_ROW ASC")
	fun getAll(): List<GitHubRepoEntity>

	@Query("SELECT * FROM $TABLE_NAME ORDER BY $NAME_ROW ASC")
	fun getAllLive(): LiveData<List<GitHubRepoEntity>>

	@Query("DELETE FROM $TABLE_NAME")
	fun removeAll()

}