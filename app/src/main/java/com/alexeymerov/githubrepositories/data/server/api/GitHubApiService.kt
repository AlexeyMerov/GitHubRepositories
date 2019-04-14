package com.alexeymerov.githubrepositories.data.server.api

import com.alexeymerov.githubrepositories.data.server.pojo.response.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiService {

	enum class SORTING {
		STARS
	}

	@GET("search/repositories")
	fun searchRepositories(@Query("q") query: String,
						   @Query("sort") sortType: SORTING,
						   @Query("page") pageNum: Int,
						   @Query("per_page") perPage: Int): Single<SearchResponse>

}
