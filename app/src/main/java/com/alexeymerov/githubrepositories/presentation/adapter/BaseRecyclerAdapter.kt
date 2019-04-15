package com.alexeymerov.githubrepositories.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alexeymerov.githubrepositories.utils.extensions.AutoUpdatableAdapter
import kotlin.properties.Delegates

abstract class BaseRecyclerAdapter<T : Any, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH>(),
	AutoUpdatableAdapter<T> {

	var items: List<T> by Delegates.observable(emptyList()) { _, oldSet, newSet -> autoNotify(oldSet, newSet) }

	override fun getItemCount() = items.size

	override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items.elementAt(position))

	override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) = when {
		payloads.isEmpty() -> onBindViewHolder(holder, position)
		else -> proceedPayloads(payloads, holder, position)
	}

	abstract fun proceedPayloads(payloads: MutableList<Any>, holder: VH, position: Int)
}

abstract class BaseViewHolder<in T : Any>(containerView: View) : RecyclerView.ViewHolder(containerView) {
	abstract fun bind(currentItem: T)
}
