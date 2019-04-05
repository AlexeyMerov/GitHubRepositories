package com.alexeymerov.githubrepositories.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexeymerov.githubrepositories.data.database.entity.GitHubRepoEntity
import com.alexeymerov.githubrepositories.data.repository.contracts.IGitHubReposRepository
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IGitHubReposViewModel

class ReposViewModel(application: Application, private val repository: IGitHubReposRepository) :
	IGitHubReposViewModel(application) {

	override val repositoryList: LiveData<List<GitHubRepoEntity>> = MutableLiveData<List<GitHubRepoEntity>>()

	override fun onCleared() {
		repository.onCleared()
		super.onCleared()
	}
}