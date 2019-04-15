package com.alexeymerov.githubrepositories.presentation.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexeymerov.githubrepositories.R
import com.alexeymerov.githubrepositories.R.string
import com.alexeymerov.githubrepositories.domain.model.GHRepoEntity
import com.alexeymerov.githubrepositories.presentation.adapter.RepositoriesRecyclerAdapter
import com.alexeymerov.githubrepositories.presentation.base.BaseActivity
import com.alexeymerov.githubrepositories.presentation.di.ViewModelComponent
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel
import com.alexeymerov.githubrepositories.utils.EndlessRecyclerViewScrollListener
import com.alexeymerov.githubrepositories.utils.SPHelper
import com.alexeymerov.githubrepositories.utils.debugLog
import com.alexeymerov.githubrepositories.utils.errorLog
import com.alexeymerov.githubrepositories.utils.extensions.circleReveal
import com.alexeymerov.githubrepositories.utils.extensions.getColorEx
import com.alexeymerov.githubrepositories.utils.extensions.onExpandListener
import com.alexeymerov.githubrepositories.utils.extensions.onTextChanged
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_repositories.imageRecycler
import kotlinx.android.synthetic.main.activity_repositories.progressBar
import kotlinx.android.synthetic.main.activity_repositories.searchToolbar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchReposActivity : BaseActivity() {

	private val RC_SIGN_IN = 9988

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	private val viewModel by lazy { getViewModel<IReposViewModel>(viewModelFactory) }

	private val reposRecyclerAdapter by lazy { initRecyclerAdapter() }
	private val layoutManager by lazy { initLayoutManager() }
	private val searchSubject by lazy { PublishSubject.create<String>() }

	private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
	private val isUserAuthorized: Boolean
		get() = firebaseAuth.currentUser != null

	private lateinit var searchMenu: Menu
	private lateinit var menuItemSearch: MenuItem
	private lateinit var lastQuery: String
	private lateinit var searchDisposable: Disposable
	private lateinit var paginationListener: EndlessRecyclerViewScrollListener

	private var isInSearch = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_repositories)
		initViews()
		initObservers()
	}

	override fun injectActivity(component: ViewModelComponent) = component.injectActivity(this)

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.main_menu, menu)
		val accountItem = menu.findItem(R.id.action_login_logout)
		if (isUserAuthorized) accountItem.icon = ContextCompat.getDrawable(this, R.drawable.ic_logout)
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

	private fun initSearchView() {
		val searchView = searchMenu.findItem(R.id.action_filter_search)?.actionView as SearchView
		searchView.isSubmitButtonEnabled = false
		searchView.maxWidth = Integer.MAX_VALUE
		searchView.onTextChanged { searchSubject.onNext(it) }

		val closeButton = searchView.findViewById(R.id.search_close_btn) as ImageView
		closeButton.setImageResource(R.drawable.ic_close_black)

		val txtSearch = searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
		txtSearch.hint = getString(string.search_string)
		txtSearch.setHintTextColor(Color.DKGRAY)
		txtSearch.setTextColor(getColorEx(R.color.colorPrimary))

		searchDisposable = searchSubject
			.filter { it.isNotEmpty() }
			.debounce(500, TimeUnit.MILLISECONDS)
			.subscribe {
				lastQuery = it
				paginationListener.resetState()
				viewModel.searchRepos(it)
			}
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
		viewModel.getReposList().observe(this, Observer {
			reposRecyclerAdapter.items = it
		})
	}

	private fun onAccountClicked() = if (isUserAuthorized) logoutUser() else loginGithub()

	private fun logoutUser() {
		//todo are u sure
		AuthUI.getInstance().signOut(this)
	}

	private fun loginGithub() {
		val gitHubProvider = AuthUI.IdpConfig.GitHubBuilder().build()
		val providers = arrayListOf(gitHubProvider)
		val intent = AuthUI.getInstance()
			.createSignInIntentBuilder()
			.setAvailableProviders(providers)
			.build()

		startActivityForResult(intent, RC_SIGN_IN)
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode != RC_SIGN_IN) return
		val response = IdpResponse.fromResultIntent(data)
		when (resultCode) {
			Activity.RESULT_OK -> onSuccessAuthorization(response)
			else -> errorLog(response?.error)
		}
	}

	private fun onSuccessAuthorization(response: IdpResponse?) {
		val userToken = response?.idpToken!!
		SPHelper.setShared("token", userToken)
		debugLog(firebaseAuth.currentUser?.displayName)
	}
}