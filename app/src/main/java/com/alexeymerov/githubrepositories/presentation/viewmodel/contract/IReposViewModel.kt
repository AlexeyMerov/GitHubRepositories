package com.alexeymerov.githubrepositories.presentation.viewmodel.contract

import androidx.lifecycle.LiveData
import com.alexeymerov.githubrepositories.domain.model.GHRepoEntity
import com.alexeymerov.githubrepositories.presentation.viewmodel.BaseViewModel

abstract class IReposViewModel : BaseViewModel() {

	abstract fun getReposList(): LiveData<List<GHRepoEntity>>

	abstract fun searchRepos(query: String)

	abstract fun searchRepos(query: String, pageNum: Int, perPage: Int)

}