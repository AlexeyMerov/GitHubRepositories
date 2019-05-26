package com.alexeymerov.githubrepositories.data.server.pojo.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
		@Json(name = "items")
		val items: List<RepositoryItemResponse>
)

@JsonClass(generateAdapter = true)
data class RepositoryItemResponse(
		@Json(name = "id")
		val id: Int,

		@Json(name = "name")
		val repositoryName: String,

		@Json(name = "description")
		val description: String?,

		@Json(name = "language")
		val language: String?,

		@Json(name = "owner")
		val owner: Owner,

		@Json(name = "html_url")
		val webUrl: String,

		@Json(name = "updated_at")
		val updatedAt: String,

		@Json(name = "stargazers_count")
		val starsCount: Int
)

@JsonClass(generateAdapter = true)
data class Owner(
		@Json(name = "login")
		val loginName: String
)