package com.alexeymerov.githubrepositories.presentation.activity

import android.os.Bundle
import com.alexeymerov.githubrepositories.R.layout
import com.alexeymerov.githubrepositories.presentation.base.BaseActivity

class MainActivity : BaseActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(layout.activity_main)
	}
}
