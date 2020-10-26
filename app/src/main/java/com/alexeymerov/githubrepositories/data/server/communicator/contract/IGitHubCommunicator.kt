package com.alexeymerov.githubrepositories.data.server.communicator.contract

import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService.SORTING
import com.alexeymerov.githubrepositories.data.server.pojo.response.SearchResponse
import kotlinx.coroutines.Deferred

interface IGitHubCommunicator {

	fun searchRepositoriesAsync(query: String, sortType: SORTING, pageNum: Int, perPage: Int): Deferred<SearchResponse>

}