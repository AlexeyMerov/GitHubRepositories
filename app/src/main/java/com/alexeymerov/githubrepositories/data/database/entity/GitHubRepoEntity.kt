package com.alexeymerov.githubrepositories.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexeymerov.githubrepositories.data.database.dao.GitHubReposDAO

@Entity(tableName = GitHubReposDAO.TABLE_NAME)
data class GitHubRepoEntity(
		@PrimaryKey
		val id: Long,
		val dateMillis: Long,
		val name: String

)