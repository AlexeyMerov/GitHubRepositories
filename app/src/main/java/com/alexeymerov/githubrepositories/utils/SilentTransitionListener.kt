package com.alexeymerov.githubrepositories.utils

import android.transition.Transition

open class SilentTransitionListener : Transition.TransitionListener {

	override fun onTransitionEnd(transition: Transition?) {
		onTransitionEnd()
	}

	open fun onTransitionEnd() {}

	override fun onTransitionResume(transition: Transition?) {
	}

	override fun onTransitionPause(transition: Transition?) {
	}

	override fun onTransitionCancel(transition: Transition?) {
	}

	override fun onTransitionStart(transition: Transition?) {
	}
}