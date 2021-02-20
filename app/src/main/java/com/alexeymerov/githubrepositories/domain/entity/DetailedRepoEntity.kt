package com.alexeymerov.githubrepositories.domain.entity

data class DetailedRepoEntity(
		val id: Int,
		val ownerName: String,
		val repositoryName: String,
		val description: String?,
		val language: String?,
		val webUrl: String,
		val updatedAt: String?,
		val starsCount: String
)