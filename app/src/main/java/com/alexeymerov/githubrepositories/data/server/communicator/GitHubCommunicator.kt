package com.alexeymerov.githubrepositories.data.server.communicator

import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService
import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService.SORTING
import com.alexeymerov.githubrepositories.data.server.communicator.contract.IGitHubCommunicator
import com.alexeymerov.githubrepositories.data.server.pojo.response.SearchResponse
import io.reactivex.Single
import javax.inject.Inject

class GitHubCommunicator @Inject constructor(apiService: GitHubApiService)
	: BaseCommunicator<GitHubApiService>(apiService), IGitHubCommunicator {

	override fun searchRepositories(query: String, sortType: SORTING, pageNum: Int,
									perPage: Int): Single<SearchResponse> =
		apiService.searchRepositories(query, sortType, pageNum, perPage)

}