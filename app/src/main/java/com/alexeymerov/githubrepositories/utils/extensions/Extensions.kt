package com.alexeymerov.githubrepositories.utils.extensions

import android.content.res.Resources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

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

fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun currentMillis() = System.currentTimeMillis()