package com.alexeymerov.githubrepositories.data.server.api

import com.alexeymerov.githubrepositories.data.server.pojo.response.GitHubRepoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiService {

	@GET("repository")
	fun getRepos(@Query("name") name: String): Single<GitHubRepoResponse>

}
