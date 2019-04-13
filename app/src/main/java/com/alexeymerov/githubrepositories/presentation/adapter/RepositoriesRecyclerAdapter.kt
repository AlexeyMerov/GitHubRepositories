package com.alexeymerov.githubrepositories.presentation.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.alexeymerov.githubrepositories.R
import com.alexeymerov.githubrepositories.domain.model.GHRepoEntity
import com.alexeymerov.githubrepositories.presentation.adapter.RepositoriesRecyclerAdapter.ViewHolder
import com.alexeymerov.githubrepositories.utils.extensions.inflate
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

class RepositoriesRecyclerAdapter() : BaseRecyclerAdapter<GHRepoEntity, ViewHolder>() {

	private lateinit var glideRequests: RequestManager
	private lateinit var onRepoClicked: (GHRepoEntity, View) -> Unit

	constructor(context: Context, onImageClicked: (GHRepoEntity, View) -> Unit) : this() {
		glideRequests = Glide.with(context) //todo check correctness
		onRepoClicked = onImageClicked
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		RepositoryViewHolder(parent.inflate(R.layout.item_repo))

	override fun getItemViewType(position: Int) = 0

	override fun compareItems(old: GHRepoEntity, new: GHRepoEntity) = old.id == new.id

	override fun compareContentForPayload(old: GHRepoEntity, new: GHRepoEntity) = emptyList<GHRepoEntity>()

	override fun proceedPayloads(payloads: MutableList<Any>, holder: ViewHolder, position: Int) {
		// handle changes from payloads
		holder.bind(items.elementAt(position))
	}

	abstract inner class ViewHolder(val containerView: View) : BaseViewHolder<GHRepoEntity>(containerView) {
		override fun bind(currentItem: GHRepoEntity) {
			// do something base things
		}
	}

	inner class RepositoryViewHolder(containerView: View) : ViewHolder(containerView) {

		private val nameTextView by lazy { containerView.findViewById<TextView>(R.id.name_tv) }

		override fun bind(currentItem: GHRepoEntity) {
			super.bind(currentItem)
			nameTextView.text = currentItem.name
		}
	}

}
