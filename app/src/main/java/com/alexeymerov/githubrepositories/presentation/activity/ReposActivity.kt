package com.alexeymerov.githubrepositories.presentation.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexeymerov.githubrepositories.R
import com.alexeymerov.githubrepositories.data.database.entity.GitHubRepoEntity
import com.alexeymerov.githubrepositories.presentation.adapter.RepositoriesRecyclerAdapter
import com.alexeymerov.githubrepositories.presentation.base.BaseActivity
import com.alexeymerov.githubrepositories.presentation.di.ViewModelComponent
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel
import com.alexeymerov.githubrepositories.utils.EndlessRecyclerViewScrollListener
import com.alexeymerov.githubrepositories.utils.extensions.dpToPx
import com.alexeymerov.githubrepositories.utils.extensions.getColorEx
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_repos.imageRecycler
import kotlinx.android.synthetic.main.activity_repos.progressBar
import kotlinx.android.synthetic.main.activity_repos.searchToolbar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReposActivity : BaseActivity() {

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	private val viewModel by lazy { getViewModel<IReposViewModel>(viewModelFactory) }

	private val reposRecyclerAdapter by lazy { initRecyclerAdapter() }
	private val layoutManager by lazy { initLayoutManager() }
	private var isInSearch = false
	private lateinit var searchMenu: Menu
	private lateinit var menuItemSearch: MenuItem
	private lateinit var lastQuery: String
	private lateinit var searchDisposable: Disposable
	private lateinit var paginationListener: EndlessRecyclerViewScrollListener

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_repos)

		initViews()
		initObservers()
	}

	override fun injectActivity(component: ViewModelComponent) = component.injectActivity(this)

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.main_menu, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.action_search) {
			menuItemSearch.expandActionView()
			searchToolbar.circleReveal(true)
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onDestroy() {
		searchDisposable.dispose()
		super.onDestroy()
	}

	private fun initViews() {
		initToolbar(getString(R.string.toolbar_title))
		initSearchToolbar()
		initRecycler()
	}

	private fun initSearchToolbar() {
		searchToolbar.inflateMenu(R.menu.menu_search)
		searchToolbar.setNavigationOnClickListener { searchToolbar.circleReveal(false) }

		searchMenu = searchToolbar.menu
		menuItemSearch = searchMenu.findItem(R.id.action_filter_search)
		menuItemSearch.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
			override fun onMenuItemActionExpand(item: MenuItem): Boolean {
				searchToolbar.circleReveal(true)
				isInSearch = true
				return true
			}

			override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
				searchToolbar.circleReveal(false)
				isInSearch = false
//				viewModel.loadImages()
				return true
			}
		})
		initSearchView()
	}

	private val searchSubject = PublishSubject.create<String>()

	private fun initSearchView() {
		val searchView = searchMenu.findItem(R.id.action_filter_search)?.actionView as SearchView
		searchView.isSubmitButtonEnabled = false
		searchView.maxWidth = Integer.MAX_VALUE

		val closeButton = searchView.findViewById(R.id.search_close_btn) as ImageView
		closeButton.setImageResource(R.drawable.ic_close_black)

		val txtSearch = searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
		txtSearch.hint = "Search..."
		txtSearch.setHintTextColor(Color.DKGRAY)
		txtSearch.setTextColor(getColorEx(R.color.colorPrimary))

		searchDisposable = searchSubject
			.filter { !it.isEmpty() }
			.debounce(500, TimeUnit.MILLISECONDS)
			.subscribe {
				lastQuery = it.toString()
				paginationListener.resetState()

			}
	}

	private fun initLayoutManager() = LinearLayoutManager(this).apply {
		isMeasurementCacheEnabled = true
		isItemPrefetchEnabled = true
		orientation = RecyclerView.VERTICAL
	}

	private fun initRecyclerAdapter() = RepositoriesRecyclerAdapter(this, ::onRepoClicked)

	private fun onRepoClicked(gitHubRepoEntity: GitHubRepoEntity, view: View) {

	}

	private fun initRecycler() {
		paginationListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
			override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
				progressBar.visibility = View.VISIBLE

			}
		}
		imageRecycler.also {
			it.setItemViewCacheSize(30)
			it.layoutManager = layoutManager
			it.adapter = reposRecyclerAdapter
			it.addOnScrollListener(paginationListener)
		}
	}

	private fun initObservers() {

	}

	private fun View.circleReveal(needShow: Boolean) = circleReveal(id, needShow)

	private fun circleReveal(viewId: Int, needShow: Boolean) {
		val myView = findViewById<View>(viewId)
		val width = myView.width
		val centerX = width - 28.dpToPx()
		val centerY = myView.height / 2
		val startRadius = if (needShow) 0f else width.toFloat()
		val endRadius = if (needShow) width.toFloat() else 0f
		if (needShow) myView.visibility = View.VISIBLE

		val anim = ViewAnimationUtils.createCircularReveal(myView, centerX, centerY, startRadius, endRadius)
		anim.apply {
			addListener(object : AnimatorListenerAdapter() {
				override fun onAnimationEnd(animation: Animator) {
					if (!needShow) myView.visibility = View.INVISIBLE
				}
			})
			duration = 350L
			start()
		}
	}
}
