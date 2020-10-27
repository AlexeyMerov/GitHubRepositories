package com.alexeymerov.githubrepositories.domain.usecase.contract

import com.alexeymerov.githubrepositories.domain.model.DetailedRepoEntity
import com.alexeymerov.githubrepositories.domain.model.ListRepoEntity
import kotlinx.coroutines.flow.Flow

interface IReposUseCase : IUseCase<ListRepoEntity> {

	fun searchRepositories(query: String)

	fun searchRepositories(query: String, pageNum: Int, perPage: Int)

	fun getReposList(): Flow<List<ListRepoEntity>>

	suspend fun getRepoDetails(repoId: Int): DetailedRepoEntity

}