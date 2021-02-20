package com.alexeymerov.githubrepositories.presentation.viewmodel.contract

import androidx.lifecycle.LiveData
import com.alexeymerov.githubrepositories.domain.entity.DetailedRepoEntity
import com.alexeymerov.githubrepositories.presentation.viewmodel.BaseViewModel

abstract class IRepoDetailedViewModel : BaseViewModel() {

	abstract fun getDetailedRepoState(): LiveData<DetailedRepoState>

	abstract fun findRepoDetails(repoId: Int)

	sealed class DetailedRepoState {
		object Default : DetailedRepoState()
		class Found(val entity: DetailedRepoEntity) : DetailedRepoState()
		class Error(val exception: Exception) : DetailedRepoState()
	}

}