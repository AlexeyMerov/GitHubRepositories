package com.alexeymerov.githubrepositories.domain.usecase

import com.alexeymerov.githubrepositories.data.repository.GitHubReposRepository
import com.alexeymerov.githubrepositories.domain.usecase.contract.IReposUseCase
import javax.inject.Inject

class ReposUseCase @Inject constructor(val repository: GitHubReposRepository) : IReposUseCase {

	override fun getTest() = repository.getTest()

	override fun clean() {

	}
}