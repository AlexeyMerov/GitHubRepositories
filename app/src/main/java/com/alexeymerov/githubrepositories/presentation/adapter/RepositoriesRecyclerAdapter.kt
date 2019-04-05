package com.alexeymerov.githubrepositories.presentation.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.alexeymerov.githubrepositories.R
import com.alexeymerov.githubrepositories.data.database.entity.GitHubRepoEntity
import com.alexeymerov.githubrepositories.presentation.adapter.RepositoriesRecyclerAdapter.ViewHolder
import com.alexeymerov.githubrepositories.utils.extensions.inflate
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

class RepositoriesRecyclerAdapter() : BaseRecyclerAdapter<GitHubRepoEntity, ViewHolder>() {

	private lateinit var glideRequests: RequestManager
	private lateinit var onRepoClicked: (GitHubRepoEntity, View) -> Unit

	constructor(context: Context, onImageClicked: (GitHubRepoEntity, View) -> Unit) : this() {
		glideRequests = Glide.with(context) //todo check correctness
		onRepoClicked = onImageClicked
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		RepositoryViewHolder(parent.inflate(R.layout.item_repo))

	override fun getItemViewType(position: Int) = 0

	override fun compareItems(old: GitHubRepoEntity, new: GitHubRepoEntity) = old.id == new.id

	override fun compareContentForPayload(old: GitHubRepoEntity, new: GitHubRepoEntity) = emptyList<GitHubRepoEntity>()

	override fun proceedPayloads(payloads: MutableList<Any>, holder: ViewHolder, position: Int) {
		// handle changes from payloads
		holder.bind(items.elementAt(position))
	}

	abstract inner class ViewHolder(val containerView: View) : BaseViewHolder<GitHubRepoEntity>(containerView) {
		override fun bind(currentItem: GitHubRepoEntity) {
			// do something base things
		}
	}

	inner class RepositoryViewHolder(containerView: View) : ViewHolder(containerView) {
		override fun bind(currentItem: GitHubRepoEntity) {
			super.bind(currentItem)
			containerView.apply {

			}
		}
	}

}
