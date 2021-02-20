package com.alexeymerov.githubrepositories.domain.mapper

import com.alexeymerov.githubrepositories.data.database.entity.GHRepoDBEntity
import com.alexeymerov.githubrepositories.domain.entity.DetailedRepoEntity
import com.alexeymerov.githubrepositories.domain.entity.ListRepoEntity
import com.alexeymerov.githubrepositories.utils.extensions.formatK
import com.alexeymerov.githubrepositories.utils.extensions.formatM
import com.alexeymerov.githubrepositories.utils.extensions.getDateFromString
import com.alexeymerov.githubrepositories.utils.extensions.getPrettyDateString
import javax.inject.Inject

class UseCaseReposMapper @Inject constructor() {

	fun mapToListItem(list: List<GHRepoDBEntity>): List<ListRepoEntity> {
		val resultList = ArrayList<ListRepoEntity>(list.size)
		for (item in list) {
			resultList.add(mapToListItem(item))
		}
		return resultList
	}

	private fun mapToListItem(item: GHRepoDBEntity) = with(item) {
		ListRepoEntity(
				id = id,
				repositoryName = repositoryName,
				description = formatDescription(description),
				language = language,
				updatedAt = formatUpdateDate(updatedAt),
				starsCount = formatStarsNumber(starsCount)
		)
	}

	fun mapToDetailedItem(item: GHRepoDBEntity) = with(item) {
		DetailedRepoEntity(
				id = id,
				ownerName = ownerLoginName,
				repositoryName = repositoryName,
				description = description,
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