package com.alexeymerov.githubrepositories.utils.extensions

import android.app.Activity
import android.content.Context
import android.view.Menu
import androidx.annotation.ColorRes
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat

fun Context.getColorEx(@ColorRes colorId: Int) = ContextCompat.getColor(this, colorId)

fun Activity.inflateMenu(@MenuRes menuRes: Int, menu: Menu): Boolean {
	menuInflater.inflate(menuRes, menu)
	return true
}
