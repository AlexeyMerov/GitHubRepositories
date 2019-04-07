package com.alexeymerov.githubrepositories.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexeymerov.githubrepositories.data.database.entity.GitHubRepoEntity
import com.alexeymerov.githubrepositories.domain.usecase.contract.IReposUseCase
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel
import javax.inject.Inject

class ReposViewModel
@Inject constructor(private val reposUseCase: IReposUseCase)
	: IReposViewModel() {

	override val repositoryList: LiveData<List<GitHubRepoEntity>> = MutableLiveData<List<GitHubRepoEntity>>()

	override fun getTest() = reposUseCase.getTest()

	override fun onCleared() {
		reposUseCase.clean()
		super.onCleared()
	}
}