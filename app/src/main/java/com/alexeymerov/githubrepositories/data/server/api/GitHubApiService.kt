package com.alexeymerov.githubrepositories.data.server.api

import com.alexeymerov.githubrepositories.data.server.pojo.response.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiService {

	enum class SORTING {
		STARS
	}

	@GET("search/repositories")
	fun searchRepositoriesAsync(@Query("q") query: String,
								@Query("sort") sortType: SORTING,
								@Query("page") pageNum: Int,
								@Query("per_page") perPage: Int): Deferred<SearchResponse>

}
