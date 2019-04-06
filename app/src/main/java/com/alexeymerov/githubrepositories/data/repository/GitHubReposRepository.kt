package com.alexeymerov.githubrepositories.data.repository

import com.alexeymerov.githubrepositories.data.database.dao.GitHubReposDAO
import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import com.alexeymerov.githubrepositories.data.server.communicator.GitHubCommunicator
import javax.inject.Inject

class GitHubReposRepository
@Inject constructor(private val gitHubCommunicator: GitHubCommunicator, private val gitHubReposDAO: GitHubReposDAO)
	: IGitHubReposRepository() {

	override fun getTest() = gitHubCommunicator.getTest()

}