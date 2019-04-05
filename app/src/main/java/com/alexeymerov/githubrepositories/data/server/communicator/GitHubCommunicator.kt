package com.alexeymerov.githubrepositories.data.server.communicator

import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService

class GitHubCommunicator(apiUrl: String) : BaseCommunicator<GitHubApiService>(GitHubApiService::class.java, apiUrl) {

}