package com.alexeymerov.githubrepositories.presentation.di.scope

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CONSTRUCTOR
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY_GETTER
import kotlin.annotation.AnnotationTarget.PROPERTY_SETTER
import kotlin.reflect.KClass

@MustBeDocumented
@Retention(RUNTIME)
@Target(FUNCTION, CONSTRUCTOR, PROPERTY_GETTER, PROPERTY_SETTER)
@MapKey
annotation class ViewModelKey(val viewModel: KClass<out ViewModel>)