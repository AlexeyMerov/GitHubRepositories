package com.alexeymerov.githubrepositories.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.alexeymerov.githubrepositories.domain.usecase.contract.IReposUseCase
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IRepoDetailedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(private var reposUseCase: IReposUseCase) : IRepoDetailedViewModel() {

	private val searchState = MutableLiveData<DetailedRepoState>(DetailedRepoState.Default)

	override fun getDetailedRepoState() = searchState

	override fun findRepoDetails(repoId: Int) {
		launch {
			val repoDetails = reposUseCase.getRepoDetails(repoId)
			withContext(Dispatchers.Main) {
				searchState.value = (DetailedRepoState.Found(repoDetails))
			}
		}
	}
}