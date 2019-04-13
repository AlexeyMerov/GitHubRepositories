package com.alexeymerov.githubrepositories.domain.usecase.contract

import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService.SORTING
import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService.SORTING.STARS
import com.alexeymerov.githubrepositories.domain.model.GHRepoEntity
import io.reactivex.Flowable

interface IReposUseCase : IUseCase<GHRepoEntity> {

	fun searchRepositories(query: String)

	fun searchRepositories(query: String, sortType: SORTING = STARS, pageNum: Int, perPage: Int)

	fun getReposList(): Flowable<List<GHRepoEntity>>

}