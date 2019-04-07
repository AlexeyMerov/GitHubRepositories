package com.alexeymerov.githubrepositories.presentation.di

import com.alexeymerov.githubrepositories.domain.di.UseCaseComponent
import com.alexeymerov.githubrepositories.presentation.activity.MainActivity
import com.alexeymerov.githubrepositories.presentation.activity.ReposActivity
import com.alexeymerov.githubrepositories.presentation.di.scope.ActivityScope
import dagger.Component

@ActivityScope
@Component(modules = [ViewModelModule::class], dependencies = [UseCaseComponent::class])
interface ViewModelComponent {

	fun injectActivity(activity: MainActivity)

	fun injectActivity(activity: ReposActivity)

}