package com.alexeymerov.githubrepositories.data.repository.contracts

import com.alexeymerov.githubrepositories.data.database.entity.GHRepoDBEntity
import com.alexeymerov.githubrepositories.data.repository.BaseRepository
import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService.SORTING
import kotlinx.coroutines.flow.Flow

abstract class IGitHubReposRepository : BaseRepository() {

	abstract fun searchRepositories(query: String, sortType: SORTING, pageNum: Int, perPage: Int,
									needRemoveLastItems: Boolean)

	abstract fun getReposListFlow(): Flow<List<GHRepoDBEntity>>

	abstract fun clearLocalData()

}