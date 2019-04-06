package com.alexeymerov.githubrepositories.domain.usecase.contract

import com.alexeymerov.githubrepositories.domain.model.RepoModel

interface IReposUseCase : IUseCase<RepoModel> {

	fun getTest(): String

}