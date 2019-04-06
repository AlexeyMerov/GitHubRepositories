package com.alexeymerov.githubrepositories.data.server.communicator

import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService
import javax.inject.Inject

class GitHubCommunicator @Inject constructor(apiService: GitHubApiService)
	: BaseCommunicator<GitHubApiService>(apiService) {

	fun getTest(): String {
		return "Communicator test"
	}

}