package com.alexeymerov.githubrepositories.presentation.di

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class, FragmentComponent::class)
class ActivityModule {

	@Provides
	fun provideReposLayoutManager(@ActivityContext context: Context) = LinearLayoutManager(context).apply {
		isMeasurementCacheEnabled = true
		isItemPrefetchEnabled = true
		orientation = RecyclerView.VERTICAL
	}

}