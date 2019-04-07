package com.alexeymerov.githubrepositories.domain.usecase

import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import com.alexeymerov.githubrepositories.domain.usecase.contract.IReposUseCase
import javax.inject.Inject

class ReposUseCase @Inject constructor(val repository: IGitHubReposRepository) : IReposUseCase {

	override fun getTest() = repository.getTest()

	override fun clean() {

	}
}