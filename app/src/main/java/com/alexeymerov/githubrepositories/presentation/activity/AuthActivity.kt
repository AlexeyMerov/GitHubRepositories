package com.alexeymerov.githubrepositories.presentation.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.alexeymerov.githubrepositories.R
import com.alexeymerov.githubrepositories.presentation.base.BaseActivity
import com.alexeymerov.githubrepositories.presentation.di.ViewModelComponent
import com.alexeymerov.githubrepositories.presentation.viewmodel.contract.IReposViewModel
import com.alexeymerov.githubrepositories.utils.debugLog
import com.alexeymerov.githubrepositories.utils.errorLog
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthActivity : BaseActivity() {

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	private val viewModel by lazy { getViewModel<IReposViewModel>(viewModelFactory) }

	private lateinit var auth: FirebaseAuth

	val stateView by lazy { findViewById<TextView>(R.id.state) }
	val buttonView by lazy { findViewById<View>(R.id.button) }

	val RC_SIGN_IN = 9988

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		auth = FirebaseAuth.getInstance()

		buttonView.setOnClickListener {
			val gitHubProvider = AuthUI.IdpConfig.GitHubBuilder().build()
			val providers = arrayListOf(gitHubProvider)
			val intent = AuthUI.getInstance()
				.createSignInIntentBuilder()
				.setAvailableProviders(providers)
				.build()

			startActivityForResult(intent, RC_SIGN_IN)

		}

//		val token = "<GITHUB-ACCESS-TOKEN>"
//		val credential = GithubAuthProvider.getCredential(token)
//
//		buttonView.setOnClickListener {
//			auth.signInWithCredential(credential)
//				.addOnCompleteListener(this) { task ->
//					if (!task.isSuccessful) {
//						Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
//					}
//
//				}
//		}

	}

	override fun onStart() {
		super.onStart()
//		val currentUser = auth.currentUser
//		stateView.text = currentUser?.displayName ?: "null"
	}

	override fun injectActivity(component: ViewModelComponent) = component.injectActivity(this)

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (requestCode == RC_SIGN_IN) {
			val response = IdpResponse.fromResultIntent(data)

			if (resultCode == Activity.RESULT_OK) {
				val user = FirebaseAuth.getInstance().currentUser
				debugLog(user?.displayName)
			} else {
				errorLog("${response?.error?.errorCode} # ${response?.error?.cause} # ${response?.error?.message}")
				errorLog(response?.error)
			}
		}
	}
}
