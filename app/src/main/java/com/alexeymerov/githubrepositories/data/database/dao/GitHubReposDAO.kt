package com.alexeymerov.githubrepositories.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.alexeymerov.githubrepositories.data.database.entity.GHRepoDBEntity
import io.reactivex.Flowable

@Dao
abstract class GitHubReposDAO : BaseDAO<GHRepoDBEntity>() {

	companion object {
		const val TABLE_NAME: String = "repo_entity"
		const val NAME_ROW: String = "name"
	}

	@Query("SELECT * FROM $TABLE_NAME ORDER BY $NAME_ROW ASC")
	abstract fun getAll(): List<GHRepoDBEntity>

	@Query("SELECT * FROM $TABLE_NAME ORDER BY $NAME_ROW ASC")
	abstract fun getAllLive(): Flowable<List<GHRepoDBEntity>>

	@Query("DELETE FROM $TABLE_NAME")
	abstract fun removeAll()

}