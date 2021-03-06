package com.alexeymerov.githubrepositories.domain.usecase

import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService.SORTING.STARS
import com.alexeymerov.githubrepositories.domain.entity.DetailedRepoEntity
import com.alexeymerov.githubrepositories.domain.entity.ListRepoEntity
import com.alexeymerov.githubrepositories.domain.mapper.UseCaseReposMapper
import com.alexeymerov.githubrepositories.domain.usecase.contract.IReposUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
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

	override fun getReposList(): Flow<List<ListRepoEntity>> = repository.getReposListFlow().map(mapper::mapToListItem).flowOn(Dispatchers.IO)

	override suspend fun getRepoDetails(repoId: Int): DetailedRepoEntity {
		val repoDetails = repository.getRepoDetails(repoId)
		return mapper.mapToDetailedItem(repoDetails)
	}

	override fun clean() = repository.clean()
}