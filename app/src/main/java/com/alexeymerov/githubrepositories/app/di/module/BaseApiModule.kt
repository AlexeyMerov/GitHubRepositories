package com.alexeymerov.githubrepositories.app.di.module

import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS

@Module
class BaseApiModule {

	@Provides
	fun provideOkHttpClientBuilder() = OkHttpClient.Builder()
		.connectionPool(ConnectionPool(5, 30, SECONDS))
		.connectTimeout(30, TimeUnit.SECONDS)
		.readTimeout(30, TimeUnit.SECONDS)
		.writeTimeout(30, TimeUnit.SECONDS)
		.apply {
			val httpLoggingInterceptor = HttpLoggingInterceptor()
			httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
			addInterceptor(httpLoggingInterceptor)
//            .addNetworkInterceptor(StethoInterceptor())
		}!!

	@Provides
	fun provideOkHttpClient(clientBuilder: Builder): OkHttpClient = clientBuilder.build()

	@Provides
	fun provideJsonConverter(): Converter.Factory = MoshiConverterFactory.create()

	@Provides
	fun provideRetrofitBuilder(client: OkHttpClient, factory: Converter.Factory) = Retrofit.Builder()
		.client(client)
		.addConverterFactory(factory)
		.addCallAdapterFactory(RxJava2CallAdapterFactory.create())!!

	@Provides
	fun provideRetrofit(retrofitBuilder: Retrofit.Builder, serverUrl: String) =
		retrofitBuilder.baseUrl(serverUrl).build()!!

}