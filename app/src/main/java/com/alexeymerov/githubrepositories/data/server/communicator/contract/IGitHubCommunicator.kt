package com.alexeymerov.githubrepositories.data.server.communicator.contract

import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService.SORTING
import com.alexeymerov.githubrepositories.data.server.pojo.response.SearchResponse
import io.reactivex.Single

interface IGitHubCommunicator {

	fun searchRepositories(query: String, sortType: SORTING, pageNum: Int, perPage: Int): Single<SearchResponse>

}