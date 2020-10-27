package com.alexeymerov.githubrepositories.domain.model

data class ListRepoEntity(
		val id: Int,
		val repositoryName: String,
		val description: String?,
		val language: String?,
		val updatedAt: String?,
		val starsCount: String
)