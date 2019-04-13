package com.alexeymerov.githubrepositories.domain.mapper

import com.alexeymerov.githubrepositories.data.database.entity.GHRepoDBEntity
import com.alexeymerov.githubrepositories.domain.model.GHRepoEntity

class UseCaseReposMapper {

	fun mapFrom(list: List<GHRepoDBEntity>): List<GHRepoEntity> {
		val resultList = ArrayList<GHRepoEntity>(list.size)
		for (item in list) {
			resultList.add(mapFrom(item))
		}
		return resultList
	}

	fun mapFrom(item: GHRepoDBEntity) = with(item) {
		GHRepoEntity(id, name)
	}

	fun mapTo(list: List<GHRepoEntity>): List<GHRepoDBEntity> {
		val resultList = ArrayList<GHRepoDBEntity>(list.size)
		for (item in list) {
			resultList.add(mapTo(item))
		}
		return resultList
	}

	fun mapTo(item: GHRepoEntity) = with(item) {
		GHRepoDBEntity(id, name)
	}

}