package com.alexeymerov.githubrepositories.domain.usecase

import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService.SORTING.STARS
import com.alexeymerov.githubrepositories.domain.mapper.UseCaseReposMapper
import com.alexeymerov.githubrepositories.domain.model.GHRepoEntity
import com.alexeymerov.githubrepositories.domain.usecase.contract.IReposUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReposUseCase @Inject constructor(private val repository: IGitHubReposRepository,
									   private val mapper: UseCaseReposMapper) : IReposUseCase {

	override fun searchRepositories(query: String) {
		searchRepositories(query, 1, 15, true)
	}

	override fun searchRepositories(query: String, pageNum: Int, perPage: Int) {
		searchRepositories(query, pageNum, perPage, false)
	}

	private fun searchRepositories(query: String, pageNum: Int, perPage: Int, needRemoveLastItems: Boolean) {
		repository.searchRepositories(query, STARS, pageNum, perPage, needRemoveLastItems)
	}

	override fun getReposList(): Flow<List<GHRepoEntity>> = repository.getReposList().map(mapper::mapFrom)

	override fun clean() = repository.clean()
}