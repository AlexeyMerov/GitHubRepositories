package com.alexeymerov.githubrepositories.presentation.viewmodel.contract

import androidx.lifecycle.LiveData
import com.alexeymerov.githubrepositories.domain.entity.ListRepoEntity
import com.alexeymerov.githubrepositories.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.Job

abstract class ISearchReposViewModel : BaseViewModel() {

	protected var searchJob: Job? = null
	protected var lastQuery: String = ""

	abstract fun getReposList(): LiveData<List<ListRepoEntity>>

	abstract fun searchRepos(query: String)

	abstract fun searchRepos(pageNum: Int)

	abstract fun getSearchState(): LiveData<SearchState>

	abstract fun resetState()

	sealed class SearchState {
		object Default : SearchState()
		object NewSearchInProgress : SearchState()
		object LastSearchInProgress : SearchState()
		class Error(val exception: Exception) : SearchState()
	}

}