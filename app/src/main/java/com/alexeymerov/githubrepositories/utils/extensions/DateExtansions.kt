package com.alexeymerov.githubrepositories.utils.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val BASE_SERVER_FORMAT = "yyyy-MM-dd HH:mm:ss"
private val PRETTY_DATE_FORMAT = "MMM dd, yyyy"

private fun Date.getFormattedString(pattern: String) = SimpleDateFormat(pattern, Locale.getDefault()).format(this)

fun getDateFromString(dateString: String): Date = SimpleDateFormat(BASE_SERVER_FORMAT, Locale.ENGLISH).parse(dateString)!!

fun formatIsoDate(dateString: String) = dateString.replace("[TZ]".toRegex(), " ")

fun Date.getPrettyDateString(): String = getFormattedString(PRETTY_DATE_FORMAT)
