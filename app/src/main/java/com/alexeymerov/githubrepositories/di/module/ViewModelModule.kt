package com.alexeymerov.githubrepositories.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexeymerov.githubrepositories.di.scope.ViewModelKey
import com.alexeymerov.githubrepositories.presentation.viewmodel.ReposViewModel
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider

@Module(includes = [UseCaseModule::class])
abstract class ViewModelModule {

	@Binds
	abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

	@Binds
	@IntoMap
	@ViewModelKey(IReposViewModel::class)
	abstract fun bindReposViewModel(viewModel: ReposViewModel): ViewModel

}

class ViewModelFactory
@Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>)
	: ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		val viewModelProvider = viewModels[modelClass]
								?: throw IllegalArgumentException("model class $modelClass not found")
		return viewModelProvider.get() as T
	}
}