package com.alexeymerov.githubrepositories.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexeymerov.githubrepositories.R
import com.alexeymerov.githubrepositories.databinding.FragmentSearchReposBinding
import com.alexeymerov.githubrepositories.domain.model.ListRepoEntity
import com.alexeymerov.githubrepositories.presentation.adapter.RepositoriesRecyclerAdapter
import com.alexeymerov.githubrepositories.presentation.dialog.REPO_ID
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel.SearchState
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel.SearchState.Default
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel.SearchState.Error
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel.SearchState.LastSearchInProgress
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel.SearchState.NewSearchInProgress
import com.alexeymerov.githubrepositories.utils.EndlessRecyclerViewScrollListener
import com.alexeymerov.githubrepositories.utils.errorLog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchReposFragment : Fragment() {

	private val viewModel by viewModels<IReposViewModel>()

	@Inject
	lateinit var reposRecyclerAdapter: RepositoriesRecyclerAdapter

	@Inject
	lateinit var layoutManager: LinearLayoutManager

	private val paginationListener by lazy { EndlessRecyclerViewScrollListener(layoutManager) }

	private var _binding: FragmentSearchReposBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_binding = FragmentSearchReposBinding.inflate(inflater, container, false)

		initObservers()
		initViews()

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun initViews() {
		initRecycler()
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

	private fun initObservers() {
		viewModel.getReposList().observe(viewLifecycleOwner, ::onListDataUpdated)
		viewModel.getSearchState().observe(viewLifecycleOwner, ::onSearchStateUpdated)
	}

	private fun onListDataUpdated(it: List<ListRepoEntity>) {
		reposRecyclerAdapter.items = it
		toggleProgressBar(false)
	}

	private fun onSearchStateUpdated(it: SearchState) = when (it) {
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

	private fun onRepoClicked(entity: ListRepoEntity) {
		val bundle = Bundle().apply { putInt(REPO_ID, entity.id) }
		findNavController().navigate(R.id.action_searchReposFragment_to_repoDetailsDialog, bundle)
	}
}