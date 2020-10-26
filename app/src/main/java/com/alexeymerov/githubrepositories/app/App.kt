package com.alexeymerov.githubrepositories.app

import android.app.Application
import com.alexeymerov.githubrepositories.utils.SPHelper
import com.facebook.stetho.Stetho
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

	override fun onCreate() {
		super.onCreate()
		FirebaseApp.initializeApp(this)
		SPHelper.init(this, "ghrepos")
		Stetho.initializeWithDefaults(this)
	}
}