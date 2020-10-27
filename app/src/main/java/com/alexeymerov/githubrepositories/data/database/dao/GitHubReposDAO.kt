package com.alexeymerov.githubrepositories.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.alexeymerov.githubrepositories.data.database.entity.GHRepoDBEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class GitHubReposDAO : BaseDAO<GHRepoDBEntity>() {

	companion object {

		const val TABLE_NAME: String = "repo_entity"
		const val ID_ROW: String = "id"
		const val STARS_ROW: String = "starsCount"
	}

	@Query("SELECT * FROM $TABLE_NAME ORDER BY $STARS_ROW DESC")
	abstract fun getAll(): List<GHRepoDBEntity>

	@Query("SELECT * FROM $TABLE_NAME ORDER BY $STARS_ROW DESC")
	abstract fun getAllFlow(): Flow<List<GHRepoDBEntity>>

	@Query("SELECT * FROM $TABLE_NAME WHERE $ID_ROW = :repoId ")
	abstract suspend fun getRepo(repoId: Int): GHRepoDBEntity

	@Query("DELETE FROM $TABLE_NAME")
	abstract fun removeAll()

	@Query("DELETE FROM $TABLE_NAME WHERE $ID_ROW NOT IN (:idsList)")
	abstract fun removeAllNotInGinvenIds(idsList: List<Int>)

}