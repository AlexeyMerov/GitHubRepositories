package com.alexeymerov.githubrepositories.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/*
* Taken from
* https://gist.github.com/nesquena/d09dc68ff07e845cc622
* */
@ActivityRetainedScoped
class EndlessRecyclerViewScrollListener @Inject constructor(layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

	// The minimum amount of items to have below your current scroll position
	// before loading more.
	private var visibleThreshold = 5

	// The current offset index of data you have loaded
	private var currentPage = 0

	// The total number of items in the dataset after the last load
	private var previousTotalItemCount = 0

	// True if we are still waiting for the last set of data to load.
	private var loading = true

	// Sets the starting page index
	private val startingPageIndex = 1

	private var mLayoutManager: RecyclerView.LayoutManager = layoutManager

	private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
		var maxSize = 0
		for (i in lastVisibleItemPositions.indices) {
			when {
				i == 0 -> maxSize = lastVisibleItemPositions[i]
				lastVisibleItemPositions[i] > maxSize -> maxSize = lastVisibleItemPositions[i]
			}
		}
		return maxSize
	}

	override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
		var lastVisibleItemPosition = 0
		val totalItemCount = mLayoutManager.itemCount

		when (mLayoutManager) {
			is StaggeredGridLayoutManager -> {
				val lastVisibleItemPositions =
					(mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
				// get maximum element within the list
				lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
			}
			is GridLayoutManager -> lastVisibleItemPosition =
				(mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
			is LinearLayoutManager -> lastVisibleItemPosition =
				(mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
		}

		// If the total item count is zero and the previous isn't, assume the
		// list is invalidated and should be reset back to initial state
		// If it’s still loading, we check to see if the dataset count has
		// changed, if so we conclude it has finished loading and update the current page
		// number and total item count.

		// If it isn’t currently loading, we check to see if we have breached
		// the visibleThreshold and need to reload more data.
		// If we do need to reload some more data, we execute onLoadMore to fetch the data.
		// threshold should reflect how many total columns there are too

		// If the total item count is zero and the previous isn't, assume the
		// list is invalidated and should be reset back to initial state
		if (totalItemCount < previousTotalItemCount) {
			currentPage = this.startingPageIndex
			previousTotalItemCount = totalItemCount
			if (totalItemCount == 0) loading = true
		}
		// If it’s still loading, we check to see if the dataset count has
		// changed, if so we conclude it has finished loading and update the current page
		// number and total item count.
		if (loading && totalItemCount > previousTotalItemCount) {
			loading = false
			previousTotalItemCount = totalItemCount
		}

		// If it isn’t currently loading, we check to see if we have breached
		// the visibleThreshold and need to reload more data.
		// If we do need to reload some more data, we execute onLoadMore to fetch the data.
		// threshold should reflect how many total columns there are too
		if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
			currentPage++
			onLoadMore(currentPage, totalItemCount, recyclerView)
			loading = true
		}
	}

	// Call this method whenever performing new searches
	fun resetState() {
		currentPage = startingPageIndex
		previousTotalItemCount = 0
		loading = true
	}

	// Defines the process for actually loading more data based on page
	var onLoadMore: (page: Int, totalItemsCount: Int, view: RecyclerView?) -> Unit = { _, _, _ -> }
}