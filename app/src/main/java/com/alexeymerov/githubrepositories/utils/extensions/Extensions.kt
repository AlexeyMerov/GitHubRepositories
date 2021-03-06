package com.alexeymerov.githubrepositories.utils.extensions

import android.content.res.Resources
import android.view.MenuItem
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat

interface AutoUpdatableAdapter<T> {

	fun RecyclerView.Adapter<*>.autoNotify(oldList: List<T>, newList: List<T>) {
		DiffUtil.calculateDiff(object : DiffUtil.Callback() {
			override fun areItemsTheSame(oldPosition: Int, newPosition: Int) =
				compareItems(oldList[oldPosition], newList[newPosition])

			override fun areContentsTheSame(oldPosition: Int, newPosition: Int) =
				oldList[oldPosition] == newList[newPosition]

			override fun getOldListSize() = oldList.size
			override fun getNewListSize() = newList.size
			override fun getChangePayload(oldPosition: Int, newPosition: Int) =
				compareContentForPayload(oldList[oldPosition], newList[newPosition])
		}).dispatchUpdatesTo(this)
	}

	fun compareItems(old: T, new: T): Boolean

	fun compareContentForPayload(old: T, new: T): List<T>? = null
}

abstract class AsyncAutoUpdatableAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

	protected abstract val differ: AsyncListDiffer<T>

	protected val diffCallback = object : DiffUtil.ItemCallback<T>() {
		override fun areItemsTheSame(oldItem: T, newItem: T) = compareItems(oldItem, newItem)
		override fun areContentsTheSame(oldItem: T, newItem: T) = compareContent(oldItem, newItem)
		override fun getChangePayload(oldItem: T, newItem: T) = compareContentForPayload(oldItem, newItem)
	}

	fun submitList(newList: List<T>) = differ.submitList(newList)

	override fun getItemCount() = differ.currentList.size

	protected fun getListItem(position: Int): T = differ.currentList.elementAt(position)

	protected abstract fun compareItems(old: T, new: T): Boolean

	protected abstract fun compareContent(old: T, new: T): Boolean

	protected abstract fun compareContentForPayload(old: T, new: T): Any?

}

fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun currentMillis() = System.currentTimeMillis()

inline fun MenuItem.onExpandListener(crossinline onExpanded: () -> Unit, crossinline onCollapsed: () -> Unit) {
	setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
		override fun onMenuItemActionExpand(item: MenuItem): Boolean {
			onExpanded()
			return true
		}

		override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
			onCollapsed()
			return true
		}
	})
}

private val decimalFormat = DecimalFormat().apply {
	isDecimalSeparatorAlwaysShown = false
	applyPattern("#.#")
}

fun Int.formatM() = String.format("%sm", decimalFormat.format(this / 1000000.0))
fun Int.formatK() = String.format("%sk", decimalFormat.format(this / 1000.0))

