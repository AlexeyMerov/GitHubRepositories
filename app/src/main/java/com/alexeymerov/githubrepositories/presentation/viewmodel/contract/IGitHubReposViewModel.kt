package com.alexeymerov.githubrepositories.presentation.viewmodel.contract

import android.app.Application
import androidx.lifecycle.LiveData
import com.alexeymerov.githubrepositories.data.database.entity.GitHubRepoEntity
import com.alexeymerov.githubrepositories.presentation.viewmodel.BaseViewModel

abstract class IGitHubReposViewModel(application: Application) : BaseViewModel(application) {

	abstract val repositoryList: LiveData<List<GitHubRepoEntity>>

}