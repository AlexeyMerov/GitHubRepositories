package com.alexeymerov.githubrepositories.utils

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.lang.ref.WeakReference

class AuthorizationHelper(private val activity: WeakReference<AppCompatActivity>) {

	var onSuccessAction: (String?) -> Unit = {}

	private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

	val isUserAuthorized: Boolean
		get() = firebaseAuth.currentUser != null

	val currentUser: FirebaseUser?
		get() = firebaseAuth.currentUser

	fun loginGithub(onSuccess: (String) -> Unit) {
		val gitHubProvider = AuthUI.IdpConfig.GitHubBuilder().build()
		val providers = arrayListOf(gitHubProvider)
		val intent = AuthUI.getInstance()
				.createSignInIntentBuilder()
				.setAvailableProviders(providers)
				.build()
		onSuccessAction = { onSuccess(it!!) }

		authResult?.launch(intent)
	}

	private val authResult = activity.get()?.registerForActivityResult(StartActivityForResult()) { result ->
		val response = IdpResponse.fromResultIntent(result.data)
		when (result.resultCode) {
			Activity.RESULT_OK -> onSuccessAction(response?.idpToken)
			else -> errorLog(response?.error)
		}
	}

	fun logoutUser() {
		activity.get()?.apply { AuthUI.getInstance().signOut(this) }
	}

	fun onDestroy() {
		activity.clear()
	}
}