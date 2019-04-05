package com.alexeymerov.githubrepositories.domain.mapper

import com.alexeymerov.githubrepositories.data.database.entity.GitHubRepoEntity
import com.alexeymerov.githubrepositories.domain.model.RepoModel

class ReposMapper {

	fun mapGitHubRepos(list: List<GitHubRepoEntity>): List<RepoModel> {
		return emptyList()
	}

}