package com.alexeymerov.githubrepositories.di.component

import com.alexeymerov.githubrepositories.di.module.ViewModelModule
import com.alexeymerov.githubrepositories.presentation.activity.MainActivity
import com.alexeymerov.githubrepositories.presentation.activity.ReposActivity
import dagger.Component

@Component(modules = [ViewModelModule::class], dependencies = [AppComponent::class])
interface ViewModelComponent {

	fun injectActivity(activity: MainActivity)

	fun injectActivity(activity: ReposActivity)

}