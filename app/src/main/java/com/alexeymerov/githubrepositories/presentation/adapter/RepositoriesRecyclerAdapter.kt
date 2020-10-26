package com.alexeymerov.githubrepositories.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import com.alexeymerov.githubrepositories.databinding.ItemRepositoryBinding
import com.alexeymerov.githubrepositories.domain.model.GHRepoEntity
import com.alexeymerov.githubrepositories.presentation.adapter.RepositoriesRecyclerAdapter.ViewHolder
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class RepositoriesRecyclerAdapter @Inject constructor() : BaseRecyclerAdapter<GHRepoEntity, ViewHolder>() {

	lateinit var onRepoClicked: (GHRepoEntity) -> Unit

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
		val binding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return RepositoryViewHolder(binding) { onRepoClicked(items[it]) }
	}

	override fun getItemViewType(position: Int) = 0

	override fun compareItems(old: GHRepoEntity, new: GHRepoEntity) = old.id == new.id

	override fun compareContentForPayload(old: GHRepoEntity, new: GHRepoEntity) = emptyList<GHRepoEntity>()

	override fun proceedPayloads(payloads: MutableList<Any>, holder: ViewHolder, position: Int) {
		// handle changes from payloads
		holder.bind(items.elementAt(position))
	}

	abstract inner class ViewHolder(containerView: View) : BaseViewHolder<GHRepoEntity>(containerView) {

		override fun bind(currentItem: GHRepoEntity) {
			// do some base things
		}
	}

	inner class RepositoryViewHolder(private val binding: ItemRepositoryBinding, onItemClick: (Int) -> Unit) : ViewHolder(binding.root) {

		init {
			binding.root.setOnClickListener { onItemClick(bindingAdapterPosition) }
		}

		override fun bind(currentItem: GHRepoEntity) {
			super.bind(currentItem)
			with(currentItem) {
				binding.apply {
					starsCountTv.precomputeAndSetText(starsCount)
					repositoryNameTv.precomputeAndSetText(repositoryName)
					description?.let { descriptionTv.precomputeAndSetText(description) }
					language?.let { languageTv.precomputeAndSetText(language) }
					updatedAt?.let { updatedAtTv.precomputeAndSetText(updatedAt) }
				}
			}
		}

		private fun AppCompatTextView.precomputeAndSetText(text: String) {
			val textMetricsParams = TextViewCompat.getTextMetricsParams(this)
			val textFuture = PrecomputedTextCompat.getTextFuture(text, textMetricsParams, null)
			setTextFuture(textFuture)
		}
	}
}