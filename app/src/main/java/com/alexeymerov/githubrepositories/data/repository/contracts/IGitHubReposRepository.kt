package com.alexeymerov.githubrepositories.data.repository.contracts

import com.alexeymerov.githubrepositories.data.repository.BaseRepository

abstract class IGitHubReposRepository : BaseRepository() {

	abstract fun getTest(): String

}