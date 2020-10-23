package com.alexeymerov.githubrepositories.presentation.viewmodel.contract

import androidx.lifecycle.LiveData
import com.alexeymerov.githubrepositories.domain.model.GHRepoEntity
import com.alexeymerov.githubrepositories.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.Job

abstract class IReposViewModel : BaseViewModel() {

	protected var searchJob: Job? = null
	protected var lastQuery: String = ""

	abstract fun getReposList(): LiveData<List<GHRepoEntity>>

	abstract fun searchRepos(query: String)

	abstract fun searchRepos(pageNum: Int)

	abstract fun getSearchState(): LiveData<State>

	sealed class State {
		object Default : State()
		object NewSearchInProgress : State()
		object LastSearchInProgress : State()
		data class Error(val exception: Exception) : State()
	}

}