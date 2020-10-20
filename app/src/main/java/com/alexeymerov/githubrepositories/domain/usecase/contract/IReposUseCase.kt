package com.alexeymerov.githubrepositories.domain.usecase.contract

import com.alexeymerov.githubrepositories.domain.model.GHRepoEntity
import kotlinx.coroutines.flow.Flow

interface IReposUseCase : IUseCase<GHRepoEntity> {

	fun searchRepositories(query: String)

	fun searchRepositories(query: String, pageNum: Int, perPage: Int)

	fun getReposList(): Flow<List<GHRepoEntity>>

}