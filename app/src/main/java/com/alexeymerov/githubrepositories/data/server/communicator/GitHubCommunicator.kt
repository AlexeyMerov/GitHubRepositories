package com.alexeymerov.githubrepositories.data.server.communicator

import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService
import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService.SORTING
import com.alexeymerov.githubrepositories.data.server.communicator.contract.IGitHubCommunicator
import com.alexeymerov.githubrepositories.data.server.pojo.response.SearchResponse
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class GitHubCommunicator @Inject constructor(apiService: GitHubApiService)
	: BaseCommunicator<GitHubApiService>(apiService), IGitHubCommunicator {

	override fun searchRepositoriesAsync(query: String, sortType: SORTING, pageNum: Int, perPage: Int): Deferred<SearchResponse> =
		apiService.searchRepositoriesAsync(query, sortType, pageNum, perPage)

}