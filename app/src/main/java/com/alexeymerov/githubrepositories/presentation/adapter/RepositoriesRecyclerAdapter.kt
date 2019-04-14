package com.alexeymerov.githubrepositories.presentation.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.alexeymerov.githubrepositories.R
import com.alexeymerov.githubrepositories.domain.model.GHRepoEntity
import com.alexeymerov.githubrepositories.presentation.adapter.RepositoriesRecyclerAdapter.ViewHolder
import com.alexeymerov.githubrepositories.utils.extensions.inflate

class RepositoriesRecyclerAdapter() : BaseRecyclerAdapter<GHRepoEntity, ViewHolder>() {

	private lateinit var onRepoClicked: (GHRepoEntity) -> Unit

	constructor(onImageClicked: (GHRepoEntity) -> Unit) : this() {
		onRepoClicked = onImageClicked
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		RepositoryViewHolder(parent.inflate(R.layout.item_repository)) { onRepoClicked(items[it]) }

	override fun getItemViewType(position: Int) = 0

	override fun compareItems(old: GHRepoEntity, new: GHRepoEntity) = old.id == new.id

	override fun compareContentForPayload(old: GHRepoEntity, new: GHRepoEntity) = emptyList<GHRepoEntity>()

	override fun proceedPayloads(payloads: MutableList<Any>, holder: ViewHolder, position: Int) {
		// handle changes from payloads
		holder.bind(items.elementAt(position))
	}

	abstract inner class ViewHolder(containerView: View) : BaseViewHolder<GHRepoEntity>(containerView) {
		override fun bind(currentItem: GHRepoEntity) {
			// do something base things
		}
	}

	inner class RepositoryViewHolder(containerView: View, onItemClick: (Int) -> Unit) : ViewHolder(containerView) {

		init {
			containerView.setOnClickListener { onItemClick(adapterPosition) }
		}

		private val starsCount_tv = containerView.findViewById<TextView>(R.id.starsCount_tv)
		private val repositoryName_tv = containerView.findViewById<TextView>(R.id.repositoryName_tv)
		private val description_tv = containerView.findViewById<TextView>(R.id.description_tv)
		private val language_tv = containerView.findViewById<TextView>(R.id.language_tv)
		private val updatedAt_tv = containerView.findViewById<TextView>(R.id.updatedAt_tv)

		override fun bind(currentItem: GHRepoEntity) {
			super.bind(currentItem)
			with(currentItem) {
				starsCount_tv.text = starsCount
				repositoryName_tv.text = repositoryName
				description_tv.text = description
				language_tv.text = language
				updatedAt_tv.text = updatedAt
			}
		}
	}
}