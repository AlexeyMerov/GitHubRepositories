package com.alexeymerov.githubrepositories.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alexeymerov.githubrepositories.domain.usecase.contract.IReposUseCase
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReposViewModel
@ViewModelInject constructor(private var reposUseCase: IReposUseCase) : IReposViewModel() {

	private val repositoriesLiveData by lazy {
		reposUseCase.getReposList().asLiveData(viewModelScope.coroutineContext)
	}
	private val searchState = MutableLiveData<State>(State.Default)

	override fun getSearchState() = searchState

	override fun getReposList() = repositoriesLiveData

	override fun searchRepos(query: String) {
		if (query.isBlank()) resetState()
		else {
			searchJob?.cancel("Cancel on a new query")
			searchJob = launch {
				delay(400L)
				searchState.postValue(State.NewSearchInProgress)
				lastQuery = query
				reposUseCase.searchRepositories(query)
			}
		}
	}

	override fun searchRepos(pageNum: Int) {
		searchState.value = State.LastSearchInProgress
		reposUseCase.searchRepositories(lastQuery, pageNum, perPage = 15)
	}

	override fun resetState() {
		searchState.value = State.Default
	}

	override fun onCleared() {
		reposUseCase.clean()
		super.onCleared()
	}
}