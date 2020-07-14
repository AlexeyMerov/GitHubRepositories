package com.alexeymerov.githubrepositories.data.repository

import com.alexeymerov.githubrepositories.data.database.dao.GitHubReposDAO
import com.alexeymerov.githubrepositories.data.database.entity.GHRepoDBEntity
import com.alexeymerov.githubrepositories.data.mapper.ResponseMapper
import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import com.alexeymerov.githubrepositories.data.server.api.GitHubApiService.SORTING
import com.alexeymerov.githubrepositories.data.server.communicator.contract.IGitHubCommunicator
import com.alexeymerov.githubrepositories.utils.errorLog
import io.reactivex.Flowable
import javax.inject.Inject

class GitHubReposRepository
@Inject constructor(private val gitHubCommunicator: IGitHubCommunicator,
					private val gitHubReposDAO: GitHubReposDAO,
					private val reposMapper: ResponseMapper
) : IGitHubReposRepository() {

	override fun searchRepositories(query: String, sortType: SORTING, pageNum: Int, perPage: Int,
									needRemoveLastItems: Boolean) {
		gitHubCommunicator.searchRepositories(query, sortType, pageNum, perPage)
				.compose(singleTransformer())
				.map(reposMapper::mapFrom)
				.subscribe({ handleSuccessSearch(it, needRemoveLastItems) }, ::handleFailedSearch)
				.trackDisposable()
	}

	private fun handleSuccessSearch(reposEntity: List<GHRepoDBEntity>, needRemoveLastItems: Boolean) {
		if (needRemoveLastItems) {
			val idsList = ArrayList<Int>(reposEntity.size)
			for (entity in reposEntity) {
				idsList.add(entity.id)
			}
			gitHubReposDAO.removeAllNotInGinvenIds(idsList)
		}
		gitHubReposDAO.add(reposEntity)
	}

	private fun handleFailedSearch(throwable: Throwable) = errorLog(throwable)

	override fun getReposList(): Flowable<List<GHRepoDBEntity>> = gitHubReposDAO.getAllLive()

	override fun clearLocalData() = gitHubReposDAO.removeAll()
}