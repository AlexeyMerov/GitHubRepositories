package com.alexeymerov.githubrepositories.utils

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.alexeymerov.githubrepositories.R
import java.util.Locale

fun AppCompatActivity.createAlert(@StringRes titleStringId: Int,
								  @StringRes descriptionStringId: Int,
								  @StringRes positiveButtonStringId: Int = R.string.done,
								  @StringRes negativeButtonStringId: Int = R.string.cancel,
								  onCancelAction: () -> Unit = {},
								  onPositiveClick: () -> Unit = {}
) = createAlert(getString(titleStringId), getString(descriptionStringId), getString(positiveButtonStringId),
		getString(negativeButtonStringId), onCancelAction, onPositiveClick)

fun AppCompatActivity.createAlertNoCancel(@StringRes titleStringId: Int,
										  @StringRes descriptionStringId: Int,
										  @StringRes positiveButtonStringId: Int = R.string.done,
										  onPositiveClick: () -> Unit = {}
) = createAlert(getString(titleStringId), getString(descriptionStringId), getString(positiveButtonStringId),
		null, onPositiveClick)

/**
 * Creates base alert dialog with app style
 *
 * @param titleString - can be empty based on experience
 * @param descriptionString - mandatory, description text of alert
 * @param positiveButtonString - empty by default in case we don't need to show button
 * @param negativeButtonString - empty by default in case we don't need to show button
 * @param onPositiveClick - function to handle positive button clicked. not necessary
 */
@SuppressLint("InflateParams")
fun AppCompatActivity.createAlert(titleString: String = "",
								  descriptionString: String,
								  positiveButtonString: String? = null,
								  negativeButtonString: String? = null,
								  onCancelAction: () -> Unit = {},
								  onPositiveClick: () -> Unit = {}
) {
	AlertDialog.Builder(this).apply {
		setTitle(titleString)
		setMessage(descriptionString)

		positiveButtonString?.also { setPositiveButton(it.toUpperCase(Locale.getDefault())) { _, _ -> onPositiveClick() } }
		negativeButtonString?.also { setNegativeButton(it.toUpperCase(Locale.getDefault())) { _, _ -> onCancelAction() } }
		setOnDismissListener { onCancelAction() }

		create().show()
	}
}