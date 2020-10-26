package com.alexeymerov.githubrepositories.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.alexeymerov.githubrepositories.utils.AuthorizationHelper.State.Fail
import com.alexeymerov.githubrepositories.utils.AuthorizationHelper.State.Success
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseUiException
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthorizationHelper(private val registry: ActivityResultRegistry)
	: DefaultLifecycleObserver {

	private val RC_SIGN_IN = "9988"

	private lateinit var authResult: ActivityResultLauncher<Intent>

	override fun onCreate(owner: LifecycleOwner) {
		authResult = registry.register(RC_SIGN_IN, owner, StartActivityForResult()) { result ->
			val response = IdpResponse.fromResultIntent(result.data)
			val state = when {
				result.resultCode == Activity.RESULT_OK && response?.idpToken != null -> Success(response.idpToken!!)
				else -> Fail(response?.error)
			}
			onSuccessAction(state)
		}
	}

	private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

	var onSuccessAction: (State) -> Unit = {}

	val isUserAuthorized: Boolean
		get() = firebaseAuth.currentUser != null

	val currentUser: FirebaseUser?
		get() = firebaseAuth.currentUser

	fun loginGithub(onSuccess: (State) -> Unit) {
		val gitHubProvider = AuthUI.IdpConfig.GitHubBuilder().build()
		val providers = arrayListOf(gitHubProvider)
		val intent = AuthUI.getInstance()
				.createSignInIntentBuilder()
				.setAvailableProviders(providers)
				.build()
		onSuccessAction = { onSuccess(it) }

		authResult.launch(intent)
	}

	fun logoutUser(context: Context) {
		AuthUI.getInstance().signOut(context)
	}

	override fun onDestroy(owner: LifecycleOwner) {
		authResult.unregister()
		super.onDestroy(owner)
	}

	sealed class State {
		data class Success(val token: String) : State()
		data class Fail(val e: FirebaseUiException?) : State()
	}
}