package com.alexeymerov.githubrepositories.presentation.di

import androidx.hilt.lifecycle.ViewModelAssistedFactory
import androidx.lifecycle.ViewModel
import com.alexeymerov.githubrepositories.presentation.viewmodel.RepoDetailsViewModel_AssistedFactory
import com.alexeymerov.githubrepositories.presentation.viewmodel.SearchReposViewModel_AssistedFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ViewModelModule {

	// https://github.com/google/dagger/issues/1972
	companion object {

		private const val PACKAGE_NAME = "com.alexeymerov.githubrepositories.presentation.viewmodel.contract."
	}

	@Binds
	@IntoMap
	@StringKey(PACKAGE_NAME + "IReposViewModel")
	abstract fun bindReposViewModel(factory: SearchReposViewModel_AssistedFactory): ViewModelAssistedFactory<out ViewModel>

	@Binds
	@IntoMap
	@StringKey(PACKAGE_NAME + "IRepoDetailedViewModel")
	abstract fun bindRepoDetailsViewModel(factory: RepoDetailsViewModel_AssistedFactory): ViewModelAssistedFactory<out ViewModel>

}