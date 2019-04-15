package com.alexeymerov.githubrepositories.domain.mapper

import com.alexeymerov.githubrepositories.data.database.entity.GHRepoDBEntity
import com.alexeymerov.githubrepositories.domain.model.GHRepoEntity
import com.alexeymerov.githubrepositories.utils.extensions.formatK
import com.alexeymerov.githubrepositories.utils.extensions.formatM
import com.alexeymerov.githubrepositories.utils.extensions.getDateFromString
import com.alexeymerov.githubrepositories.utils.extensions.getPrettyDateString

class UseCaseReposMapper {

	fun mapFrom(list: List<GHRepoDBEntity>): List<GHRepoEntity> {
		val resultList = ArrayList<GHRepoEntity>(list.size)
		for (item in list) {
			resultList.add(mapFrom(item))
		}
		return resultList
	}

	private fun mapFrom(item: GHRepoDBEntity) = with(item) {
		GHRepoEntity(
				id = id,
				repositoryName = "$repositoryName/$ownerLoginName",
				description = formatDescription(description),
				language = language,
				webUrl = webUrl,
				updatedAt = formatUpdateDate(updatedAt),
				starsCount = formatStarsNumber(starsCount)
		)
	}

	private fun formatUpdateDate(updatedAt: String) = getDateFromString(updatedAt).getPrettyDateString()

	private fun formatDescription(string: String?) = when {
		string == null -> null
		string.length <= 30 -> string
		else -> string.substring(0, 30) + "..."
	}

	private fun formatStarsNumber(value: Int) = when {
		value > 1_000_000 -> value.formatM()
		value > 1_000 -> value.formatK()
		else -> value.toString()
	}

}