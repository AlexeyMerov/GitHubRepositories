package com.alexeymerov.githubrepositories.presentation.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexeymerov.githubrepositories.R
import com.alexeymerov.githubrepositories.R.drawable
import com.alexeymerov.githubrepositories.R.string
import com.alexeymerov.githubrepositories.domain.model.GHRepoEntity
import com.alexeymerov.githubrepositories.presentation.adapter.RepositoriesRecyclerAdapter
import com.alexeymerov.githubrepositories.presentation.base.BaseActivity
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel
import com.alexeymerov.githubrepositories.utils.AuthorizationHelper
import com.alexeymerov.githubrepositories.utils.EndlessRecyclerViewScrollListener
import com.alexeymerov.githubrepositories.utils.SPHelper
import com.alexeymerov.githubrepositories.utils.createAlert
import com.alexeymerov.githubrepositories.utils.debugLog
import com.alexeymerov.githubrepositories.utils.extensions.circleReveal
import com.alexeymerov.githubrepositories.utils.extensions.getColorEx
import com.alexeymerov.githubrepositories.utils.extensions.onExpandListener
import com.alexeymerov.githubrepositories.utils.extensions.onTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_repositories.imageRecycler
import kotlinx.android.synthetic.main.activity_repositories.progressBar
import kotlinx.android.synthetic.main.activity_repositories.searchToolbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

@AndroidEntryPoint
class SearchReposActivity : BaseActivity() {

	private val viewModel by viewModels<IReposViewModel>()

	private val reposRecyclerAdapter by lazy { initRecyclerAdapter() }
	private val layoutManager by lazy { initLayoutManager() }

	private val authHelper = AuthorizationHelper(WeakReference(this))

	private lateinit var searchMenu: Menu
	private lateinit var menuItemSearch: MenuItem
	private lateinit var lastQuery: String
	private lateinit var paginationListener: EndlessRecyclerViewScrollListener
	private lateinit var accountItem: MenuItem

	private var isInSearch = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_repositories)
		initViews()
		initObservers()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.main_menu, menu)
		accountItem = menu.findItem(R.id.action_login_logout)
		if (authHelper.isUserAuthorized) accountItem.icon = ContextCompat.getDrawable(this, R.drawable.ic_logout)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.action_search -> onSearchClicked()
			R.id.action_login_logout -> onAccountClicked()
		}
		return super.onOptionsItemSelected(item)
	}

	private fun onSearchClicked() {
		menuItemSearch.expandActionView()
		circleReveal(searchToolbar, true)
	}

	override fun onDestroy() {
		searchJob?.cancel()
		authHelper.onDestroy()
		super.onDestroy()
	}

	private fun initViews() {
		initToolbar(getString(R.string.toolbar_title))
		initSearchToolbar()
		initRecycler()
	}

	private fun initSearchToolbar() {
		searchToolbar.inflateMenu(R.menu.menu_search)
		searchToolbar.setNavigationOnClickListener { circleReveal(searchToolbar, false) }

		searchMenu = searchToolbar.menu
		menuItemSearch = searchMenu.findItem(R.id.action_filter_search)
		menuItemSearch.onExpandListener(
				onExpanded = {
					circleReveal(searchToolbar, true)
					isInSearch = true
				},
				onCollapsed = {
					circleReveal(searchToolbar, false)
					isInSearch = false
//				viewModel.loadImages()
				}
		)
		initSearchView()
	}

	private var searchJob: Job? = null
	private fun initSearchView() {
		val searchView = searchMenu.findItem(R.id.action_filter_search)?.actionView as SearchView
		searchView.isSubmitButtonEnabled = false
		searchView.maxWidth = Integer.MAX_VALUE
		searchView.onTextChanged {
			searchJob?.cancel("Cancel on a new query")
			searchJob = launch {
				delay(500L)
				lastQuery = it
				viewModel.searchRepos(it)
				paginationListener.resetState()
			}
		}

		val closeButton = searchView.findViewById(R.id.search_close_btn) as ImageView
		closeButton.setImageResource(R.drawable.ic_close_black)

		val txtSearch = searchView.findViewById(R.id.search_src_text) as EditText
		txtSearch.hint = getString(R.string.search_string)
		txtSearch.setHintTextColor(Color.DKGRAY)
		txtSearch.setTextColor(getColorEx(R.color.colorPrimary))
	}

	private fun initLayoutManager() = LinearLayoutManager(this).apply {
		isMeasurementCacheEnabled = true
		isItemPrefetchEnabled = true
		orientation = RecyclerView.VERTICAL
	}

	private fun initRecyclerAdapter() = RepositoriesRecyclerAdapter(::onRepoClicked)

	private fun onRepoClicked(entity: GHRepoEntity) {
		val uri = Uri.parse(entity.webUrl)
		val intent = Intent(Intent.ACTION_VIEW, uri)
		startActivity(intent)
	}

	private fun initRecycler() {
		paginationListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
			override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
				if (::lastQuery.isInitialized && lastQuery.isNotEmpty()) {
					progressBar.visibility = View.VISIBLE
					viewModel.searchRepos(lastQuery, page, 15)
				}
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
		viewModel.getReposList().observe(this, Observer {
			reposRecyclerAdapter.items = it
		})
	}

	private fun onAccountClicked() {
		if (authHelper.isUserAuthorized) {
			createAlert(string.sign_out, string.sign_out_description, string.ok, onPositiveClick = { logoutUser() })
		} else loginUser()
	}

	private fun loginUser() {
		authHelper.loginGithub() { userToken ->
			accountItem.icon = ContextCompat.getDrawable(this, drawable.ic_logout)
			SPHelper.setShared("token", userToken)
			debugLog(authHelper.currentUser?.displayName)
		}
	}

	private fun logoutUser() {
		authHelper.logoutUser()
		accountItem.icon = ContextCompat.getDrawable(this, R.drawable.ic_account)
	}

}