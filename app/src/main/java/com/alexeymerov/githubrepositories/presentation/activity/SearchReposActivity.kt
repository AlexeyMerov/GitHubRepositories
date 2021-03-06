package com.alexeymerov.githubrepositories.presentation.activity

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alexeymerov.githubrepositories.R
import com.alexeymerov.githubrepositories.R.drawable
import com.alexeymerov.githubrepositories.R.id
import com.alexeymerov.githubrepositories.R.string
import com.alexeymerov.githubrepositories.databinding.ActivityRepositoriesBinding
import com.alexeymerov.githubrepositories.presentation.base.BaseActivity
import com.alexeymerov.githubrepositories.presentation.viewmodel.SearchReposViewModel
import com.alexeymerov.githubrepositories.utils.AuthorizationHelper
import com.alexeymerov.githubrepositories.utils.AuthorizationHelper.State.Fail
import com.alexeymerov.githubrepositories.utils.AuthorizationHelper.State.Success
import com.alexeymerov.githubrepositories.utils.SPHelper
import com.alexeymerov.githubrepositories.utils.createAlert
import com.alexeymerov.githubrepositories.utils.debugLog
import com.alexeymerov.githubrepositories.utils.errorLog
import com.alexeymerov.githubrepositories.utils.extensions.getColorEx
import com.alexeymerov.githubrepositories.utils.extensions.onExpandListener
import com.alexeymerov.githubrepositories.utils.extensions.onTextChanged
import com.alexeymerov.githubrepositories.utils.extensions.setVisible
import dagger.hilt.android.AndroidEntryPoint

private const val KEY_USER_TOKEN = "user_token"

@AndroidEntryPoint
class SearchReposActivity : BaseActivity<ActivityRepositoriesBinding>() {

	private val viewModel by viewModels<SearchReposViewModel>()

	private lateinit var searchView: SearchView
	private lateinit var searchMenu: Menu
	private lateinit var menuItemSearch: MenuItem
	private lateinit var accountItem: MenuItem

	private lateinit var authHelper: AuthorizationHelper

	private lateinit var navController: NavController

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		initViews()
		initObservers()
		initNavigation()
	}

	override fun inflateViewBinding() = ActivityRepositoriesBinding.inflate(layoutInflater)

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.main_menu, menu)
		accountItem = menu.findItem(id.action_login_logout)
		changeAccountIcon(authHelper.isUserAuthorized)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			id.action_search -> onSearchClicked()
			id.action_login_logout -> onAccountClicked()
			else -> super.onOptionsItemSelected(item)
		}
		return true
	}

	override fun onBackPressed() {
		if (binding.searchToolbar.isVisible) {
			binding.searchToolbar.setVisible(false)
			searchView.setQuery("", true)

		} else super.onBackPressed()
	}

	private fun onSearchClicked() {
		menuItemSearch.expandActionView()
		binding.searchToolbar.setVisible()
	}

	private fun initObservers() {
		authHelper = AuthorizationHelper(activityResultRegistry)
		lifecycle.addObserver(authHelper)
	}

	private fun initViews() {
		initToolbar(getString(string.toolbar_title))
		initSearchToolbar()
	}

	private fun initNavigation() {
		val navHostFragment = supportFragmentManager.findFragmentById(id.nav_host_fragment) as NavHostFragment
		navController = navHostFragment.navController
		navController.navigate(id.searchReposFragment)
	}

	private fun initSearchToolbar() {
		binding.searchToolbar.inflateMenu(R.menu.menu_search)
		binding.searchToolbar.setNavigationOnClickListener { binding.searchToolbar.setVisible(false) }

		searchMenu = binding.searchToolbar.menu
		menuItemSearch = searchMenu.findItem(id.action_filter_search)
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
		searchView = searchMenu.findItem(id.action_filter_search)?.actionView as SearchView
		searchView.apply {
			isSubmitButtonEnabled = false
			maxWidth = Integer.MAX_VALUE
			onTextChanged(::onSearchTextChanged)
		}

		val txtSearch = searchView.findViewById<EditText>(id.search_src_text)
		txtSearch.apply {
			hint = getString(string.search_string)
			setHintTextColor(Color.DKGRAY)
			setTextColor(getColorEx(R.color.colorPrimary))
		}

		val closeButton = searchView.findViewById<ImageView>(id.search_close_btn)
		closeButton.setImageResource(drawable.ic_close_black)
	}

	private fun onSearchTextChanged(it: String) {
		viewModel.searchRepos(it)
	}

	private fun onAccountClicked() {
		when (authHelper.isUserAuthorized) {
			true -> createAlert(string.sign_out, string.sign_out_description, string.ok, onPositiveClick = { logoutUser() })
			false -> loginUser()
		}
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