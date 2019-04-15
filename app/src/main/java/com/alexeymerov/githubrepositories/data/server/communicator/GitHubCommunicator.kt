package com.alexeymerov.githubrepositories.data.server.communicator

import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService
import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService.SORTING
import com.alexeymerov.githubrepositories.data.server.communicator.contract.IGitHubCommunicator
import javax.inject.Inject

class GitHubCommunicator @Inject constructor(apiService: GitHubApiService)
	: BaseCommunicator<GitHubApiService>(apiService), IGitHubCommunicator {

	override fun searchRepositories(query: String, sortType: SORTING, pageNum: Int, perPage: Int) =
		apiService.searchRepositories(query, sortType, pageNum, perPage)

}