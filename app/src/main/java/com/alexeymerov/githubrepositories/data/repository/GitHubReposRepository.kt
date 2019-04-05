package com.alexeymerov.githubrepositories.data.repository

import com.alexeymerov.githubrepositories.data.database.GitHubReposDatabase
import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import com.alexeymerov.githubrepositories.data.server.communicator.GitHubCommunicator

class GitHubReposRepository(
		private val gitHubCommunicator: GitHubCommunicator,
		private val database: GitHubReposDatabase
) : IGitHubReposRepository() {

}