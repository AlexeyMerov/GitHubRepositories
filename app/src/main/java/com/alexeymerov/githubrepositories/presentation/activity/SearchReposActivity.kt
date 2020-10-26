package com.alexeymerov.githubrepositories.presentation.activity

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
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexeymerov.githubrepositories.R
import com.alexeymerov.githubrepositories.R.drawable
import com.alexeymerov.githubrepositories.R.string
import com.alexeymerov.githubrepositories.databinding.ActivityRepositoriesBinding
import com.alexeymerov.githubrepositories.domain.model.GHRepoEntity
import com.alexeymerov.githubrepositories.presentation.adapter.RepositoriesRecyclerAdapter
import com.alexeymerov.githubrepositories.presentation.base.BaseActivity
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel.State
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel.State.Default
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel.State.Error
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel.State.LastSearchInProgress
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel.State.NewSearchInProgress
import com.alexeymerov.githubrepositories.utils.AuthorizationHelper
import com.alexeymerov.githubrepositories.utils.AuthorizationHelper.State.Fail
import com.alexeymerov.githubrepositories.utils.AuthorizationHelper.State.Success
import com.alexeymerov.githubrepositories.utils.EndlessRecyclerViewScrollListener
import com.alexeymerov.githubrepositories.utils.SPHelper
import com.alexeymerov.githubrepositories.utils.createAlert
import com.alexeymerov.githubrepositories.utils.debugLog
import com.alexeymerov.githubrepositories.utils.errorLog
import com.alexeymerov.githubrepositories.utils.extensions.getColorEx
import com.alexeymerov.githubrepositories.utils.extensions.onExpandListener
import com.alexeymerov.githubrepositories.utils.extensions.onTextChanged
import com.alexeymerov.githubrepositories.utils.extensions.setVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchReposActivity : BaseActivity() {

	private val KEY_USER_TOKEN = "user_token"

	private val viewModel by viewModels<IReposViewModel>()

	@Inject
	lateinit var reposRecyclerAdapter: RepositoriesRecyclerAdapter

	@Inject
	lateinit var layoutManager: LinearLayoutManager

	private val paginationListener by lazy { EndlessRecyclerViewScrollListener(layoutManager) }

	private lateinit var binding: ActivityRepositoriesBinding

	private lateinit var searchMenu: Menu
	private lateinit var menuItemSearch: MenuItem
	private lateinit var accountItem: MenuItem

	private lateinit var authHelper: AuthorizationHelper

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityRepositoriesBinding.inflate(layoutInflater)
		setContentView(binding.root)
		initObservers()
		initViews()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.main_menu, menu)
		accountItem = menu.findItem(R.id.action_login_logout)
		changeAccountIcon(authHelper.isUserAuthorized)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.action_search -> onSearchClicked()
			R.id.action_login_logout -> onAccountClicked()
			else -> super.onOptionsItemSelected(item)
		}
		return true
	}

	private fun onSearchClicked() {
		menuItemSearch.expandActionView()
		binding.searchToolbar.setVisible()
	}

	private fun initObservers() {
		authHelper = AuthorizationHelper(activityResultRegistry)
		lifecycle.addObserver(authHelper)
		viewModel.getReposList().observe(this, { onListDataUpdated(it) })
		viewModel.getSearchState().observe(this, { onSearchStateUpdated(it) })
	}

	private fun onListDataUpdated(it: List<GHRepoEntity>) {
		reposRecyclerAdapter.items = it
		toggleProgressBar(false)
	}

	private fun onSearchStateUpdated(it: State) = when (it) {
		Default -> onDefaultState()
		NewSearchInProgress -> onNewSearchState()
		LastSearchInProgress -> toggleProgressBar(true)
		is Error -> onErrorState(it)
	}

	private fun onDefaultState() {
		binding.imageRecycler.clearOnScrollListeners()
		toggleProgressBar(false)
	}

	private fun onNewSearchState() {
		paginationListener.resetState()
		binding.imageRecycler.clearOnScrollListeners()
		binding.imageRecycler.addOnScrollListener(paginationListener)
		toggleProgressBar(true)
	}

	private fun onErrorState(it: Error) {
		toggleProgressBar(false)
		errorLog(it.exception)
	}

	private fun initViews() {
		initToolbar(getString(string.toolbar_title))
		initSearchToolbar()
		initRecycler()
	}

	private fun initSearchToolbar() {
		binding.searchToolbar.inflateMenu(R.menu.menu_search)
		binding.searchToolbar.setNavigationOnClickListener { binding.searchToolbar.setVisible(false) }

		searchMenu = binding.searchToolbar.menu
		menuItemSearch = searchMenu.findItem(R.id.action_filter_search)
		menuItemSearch.onExpandListener(
				onExpanded = { binding.searchToolbar.setVisible() },
				onCollapsed = {
					binding.searchToolbar.setVisible(false)
					viewModel.resetState()
				}
		)
		initSearchView()
	}

	private fun initSearchView() {
		val searchView = searchMenu.findItem(R.id.action_filter_search)?.actionView as SearchView
		searchView.isSubmitButtonEnabled = false
		searchView.maxWidth = Integer.MAX_VALUE
		searchView.onTextChanged(::onSearchTextChanged)

		val closeButton = searchView.findViewById(R.id.search_close_btn) as ImageView
		closeButton.setImageResource(drawable.ic_close_black)

		val txtSearch = searchView.findViewById(R.id.search_src_text) as EditText
		txtSearch.hint = getString(string.search_string)
		txtSearch.setHintTextColor(Color.DKGRAY)
		txtSearch.setTextColor(getColorEx(R.color.colorPrimary))
	}

	private fun onSearchTextChanged(it: String) {
		viewModel.searchRepos(it)
	}

	private fun onRepoClicked(entity: GHRepoEntity) {
		val uri = Uri.parse(entity.webUrl)
		val builder = CustomTabsIntent.Builder()
		val customTabsIntent = builder.build()
		customTabsIntent.launchUrl(this, uri)
	}

	private fun initRecycler() {
		reposRecyclerAdapter.onRepoClicked = ::onRepoClicked

		paginationListener.onNextPage = { page -> viewModel.searchRepos(page) }

		binding.imageRecycler.also {
			it.setHasFixedSize(true)
			it.layoutManager = layoutManager
			it.adapter = reposRecyclerAdapter
			it.addOnScrollListener(paginationListener)
		}
	}

	private fun toggleProgressBar(needShow: Boolean) {
		binding.progressBar.visibility = if (needShow) View.VISIBLE else View.GONE
	}

	private fun onAccountClicked() {
		if (authHelper.isUserAuthorized) {
			createAlert(string.sign_out, string.sign_out_description, string.ok, onPositiveClick = { logoutUser() })
		} else loginUser()
	}

	private fun loginUser() = authHelper.loginGithub {
		when (it) {
			is Success -> onSuccessLogin(it.token)
			is Fail -> errorLog(it.e)
		}
	}

	private fun onSuccessLogin(userToken: String) {
		changeAccountIcon(true)
		SPHelper.setShared(KEY_USER_TOKEN, userToken)
		debugLog(authHelper.currentUser?.displayName)
	}

	private fun changeAccountIcon(loggedIn: Boolean) {
		val idRes = when (loggedIn) {
			true -> drawable.ic_logout
			else -> drawable.ic_account
		}
		accountItem.icon = ContextCompat.getDrawable(this, idRes)
	}

	private fun logoutUser() {
		authHelper.logoutUser(this)
		changeAccountIcon(false)
	}

}