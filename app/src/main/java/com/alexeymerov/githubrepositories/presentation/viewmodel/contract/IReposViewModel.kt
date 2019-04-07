package com.alexeymerov.githubrepositories.presentation.viewmodel.contract

import androidx.lifecycle.LiveData
import com.alexeymerov.githubrepositories.data.database.entity.GitHubRepoEntity
import com.alexeymerov.githubrepositories.presentation.viewmodel.BaseViewModel

abstract class IReposViewModel : BaseViewModel() {

	abstract val repositoryList: LiveData<List<GitHubRepoEntity>>

	abstract fun getTest(): String

}