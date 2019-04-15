package com.alexeymerov.githubrepositories.domain.model

data class GHRepoEntity(
		val id: Int,
		val repositoryName: String,
		val description: String?,
		val language: String?,
		val webUrl: String,
		val updatedAt: String?,
		val starsCount: String
)