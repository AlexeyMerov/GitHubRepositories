package com.alexeymerov.githubrepositories.domain.usecase

import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService.SORTING
import com.alexeymerov.githubrepositories.domain.mapper.UseCaseReposMapper
import com.alexeymerov.githubrepositories.domain.model.GHRepoEntity
import com.alexeymerov.githubrepositories.domain.usecase.contract.IReposUseCase
import io.reactivex.Flowable
import javax.inject.Inject

class ReposUseCase @Inject constructor(private val repository: IGitHubReposRepository,
									   private val mapper: UseCaseReposMapper) : IReposUseCase {

	override fun searchRepositories(query: String) {
		repository.clearLocalData()
		searchRepositories(query, pageNum = 1, perPage = 15)
	}

	override fun searchRepositories(query: String, sortType: SORTING, pageNum: Int, perPage: Int) {
		repository.searchRepositories(query, sortType, pageNum, perPage)
	}

	override fun getReposList(): Flowable<List<GHRepoEntity>> = repository.getReposList().map(mapper::mapFrom)

	override fun clean() = repository.clean()
}