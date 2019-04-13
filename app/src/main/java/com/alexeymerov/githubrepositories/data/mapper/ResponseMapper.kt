package com.alexeymerov.githubrepositories.data.mapper

import com.alexeymerov.githubrepositories.data.database.entity.GHRepoDBEntity
import com.alexeymerov.githubrepositories.data.server.pojo.response.GHRepositoryResponse

class ResponseMapper {

	fun mapFrom(list: List<GHRepositoryResponse>): List<GHRepoDBEntity> {
		val resultList = ArrayList<GHRepoDBEntity>(list.size)
		for (item in list) {
			resultList.add(mapFrom(item))
		}
		return resultList
	}

	fun mapFrom(item: GHRepositoryResponse) = with(item) {
		GHRepoDBEntity(id, name)
	}

}