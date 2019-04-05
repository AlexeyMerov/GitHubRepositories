package com.alexeymerov.githubrepositories.data.server.pojo.response

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity
data class GitHubRepoResponse(
		@Json(name = "name") val nam: String
)
