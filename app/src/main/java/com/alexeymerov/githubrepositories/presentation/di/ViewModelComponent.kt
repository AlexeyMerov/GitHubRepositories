package com.alexeymerov.githubrepositories.presentation.di

import com.alexeymerov.githubrepositories.domain.di.UseCaseComponent
import com.alexeymerov.githubrepositories.presentation.activity.AuthActivity
import com.alexeymerov.githubrepositories.presentation.activity.SearchReposActivity
import com.alexeymerov.githubrepositories.presentation.di.scope.ActivityScope
import dagger.Component

@ActivityScope
@Component(modules = [ViewModelModule::class], dependencies = [UseCaseComponent::class])
interface ViewModelComponent {

	fun injectActivity(activity: AuthActivity)

	fun injectActivity(activity: SearchReposActivity)

}