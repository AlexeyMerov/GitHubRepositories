package com.alexeymerov.githubrepositories.data.repository.contracts

import com.alexeymerov.githubrepositories.data.database.entity.GHRepoDBEntity
import com.alexeymerov.githubrepositories.data.repository.BaseRepository
import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService.SORTING
import io.reactivex.Flowable

abstract class IGitHubReposRepository : BaseRepository() {

	abstract fun searchRepositories(query: String, sortType: SORTING, pageNum: Int, perPage: Int,
									needRemoveLastItems: Boolean)

	abstract fun getReposList(): Flowable<List<GHRepoDBEntity>>

	abstract fun clearLocalData()

}