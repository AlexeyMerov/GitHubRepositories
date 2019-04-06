package com.alexeymerov.githubrepositories.di.component

import android.app.Application
import com.alexeymerov.githubrepositories.di.module.DataBaseModule
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataBaseModule::class])
interface AppComponent {

//	fun getDatabase(): GitHubReposDatabase

	fun getApplicationContext(): Application

	@Component.Builder
	interface Builder {

		@BindsInstance
		fun applicationContext(application: Application): Builder

		fun build(): AppComponent
	}
}