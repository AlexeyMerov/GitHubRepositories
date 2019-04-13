package com.alexeymerov.githubrepositories.data.server.pojo.response

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity
data class GHRepositoryResponse(
		@Json(name = "name") val id: Long,
		@Json(name = "name") val name: String
)
