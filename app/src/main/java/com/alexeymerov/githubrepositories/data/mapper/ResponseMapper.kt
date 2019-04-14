package com.alexeymerov.githubrepositories.data.mapper

import com.alexeymerov.githubrepositories.data.database.entity.GHRepoDBEntity
import com.alexeymerov.githubrepositories.data.server.pojo.response.RepositoryItemResponse
import com.alexeymerov.githubrepositories.data.server.pojo.response.SearchResponse
import com.alexeymerov.githubrepositories.utils.extensions.formatIsoDate

class ResponseMapper {

	fun mapFrom(response: SearchResponse): List<GHRepoDBEntity> {
		val responseItems = response.items
		val resultList = ArrayList<GHRepoDBEntity>(responseItems.size)
		for (item in responseItems) {
			resultList.add(mapFrom(item))
		}
		return resultList
	}

	private fun mapFrom(item: RepositoryItemResponse) = with(item) {
		GHRepoDBEntity(
				id = id,
				repositoryName = repositoryName,
				description = description,
				language = language,
				webUrl = webUrl,
				updatedAt = formatIsoDate(updatedAt),
				starsCount = starsCount,
				ownerLoginName = owner.loginName
		)
	}

}