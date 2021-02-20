package com.alexeymerov.githubrepositories.presentation.dialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.viewModels
import com.alexeymerov.githubrepositories.R.string
import com.alexeymerov.githubrepositories.databinding.DialogRepoDetailsBinding
import com.alexeymerov.githubrepositories.domain.entity.DetailedRepoEntity
import com.alexeymerov.githubrepositories.presentation.viewmodel.RepoDetailsViewModel
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IRepoDetailedViewModel.DetailedRepoState
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IRepoDetailedViewModel.DetailedRepoState.Default
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IRepoDetailedViewModel.DetailedRepoState.Error
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IRepoDetailedViewModel.DetailedRepoState.Found
import com.alexeymerov.githubrepositories.utils.debugLog
import com.alexeymerov.githubrepositories.utils.errorLog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

const val REPO_ID = "repoId"

@AndroidEntryPoint
class RepoDetailsDialog : BottomSheetDialogFragment() {

	private val viewModel by viewModels<RepoDetailsViewModel>()

	private var _binding: DialogRepoDetailsBinding? = null
	private val binding get() = _binding!!

	private var repoId: Int? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		repoId = arguments?.getInt(REPO_ID)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_binding = DialogRepoDetailsBinding.inflate(inflater, container, false)

		initObservers()
		initViews()

		return binding.root
	}

	private fun initViews() {
		if (repoId == null) return
		viewModel.findRepoDetails(repoId!!)
	}

	private fun initObservers() {
		viewModel.getDetailedRepoState().observe(viewLifecycleOwner, ::onStateChanged)
	}

	private fun onStateChanged(state: DetailedRepoState) = when (state) {
		is Found -> onRepoFound(state.entity)
		is Error -> errorLog(state.exception)
		is Default -> debugLog("Default state")
	}

	private fun onRepoFound(entity: DetailedRepoEntity) {
		with(entity) {
			binding.apply {
				repositoryNameTv.text = repositoryName
				repositoryOwnerTv.text = ownerName
				starsCountTv.text = starsCount
				description?.let { descriptionTv.text = description }
				language?.let { languageTv.text = language }
				updatedAt?.let { updatedAtTv.text = updatedAt }

				shareButton.setOnClickListener {
					val sendIntent = Intent().apply {
						action = Intent.ACTION_SEND
						putExtra(Intent.EXTRA_TEXT, webUrl)
						type = "text/plain"
					}

					val shareIntent = Intent.createChooser(sendIntent, getString(string.share_to))
					startActivity(shareIntent)
				}
				openButton.setOnClickListener {
					val uri = Uri.parse(webUrl)
					val builder = CustomTabsIntent.Builder()
					val customTabsIntent = builder.build()
					customTabsIntent.launchUrl(context, uri)
				}
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}