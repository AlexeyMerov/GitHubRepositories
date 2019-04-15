package com.alexeymerov.githubrepositories.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexeymerov.githubrepositories.data.database.dao.GitHubReposDAO

@Entity(tableName = GitHubReposDAO.TABLE_NAME)
data class GHRepoDBEntity(
		@PrimaryKey
		val id: Int,
		val repositoryName: String,
		val description: String?,
		val language: String?,
		val webUrl: String,
		val updatedAt: String,
		val starsCount: Int,
		val ownerLoginName: String
)