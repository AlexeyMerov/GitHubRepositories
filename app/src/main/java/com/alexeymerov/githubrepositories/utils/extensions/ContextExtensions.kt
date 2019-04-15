package com.alexeymerov.githubrepositories.utils.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.getColorEx(@ColorRes colorId: Int) = ContextCompat.getColor(this, colorId)
