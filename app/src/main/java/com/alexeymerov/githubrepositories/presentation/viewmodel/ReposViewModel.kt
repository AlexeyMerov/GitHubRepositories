package com.alexeymerov.githubrepositories.presentation.viewmodel

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alexeymerov.githubrepositories.domain.usecase.contract.IReposUseCase
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel
import javax.inject.Inject

class ReposViewModel
@Inject constructor(private val reposUseCase: IReposUseCase) : IReposViewModel() {

	private val repositoriesLiveData by lazy { reposUseCase.getReposList().asLiveData(viewModelScope.coroutineContext) }

	override fun getReposList() = repositoriesLiveData

	override fun searchRepos(query: String) = reposUseCase.searchRepositories(query)

	override fun searchRepos(query: String, pageNum: Int, perPage: Int) {
		reposUseCase.searchRepositories(query, pageNum, perPage)
	}

	override fun onCleared() {
		reposUseCase.clean()
		super.onCleared()
	}
}