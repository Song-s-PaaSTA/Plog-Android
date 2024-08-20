package com.kpaas.plog.app.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PlogRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AccessToken