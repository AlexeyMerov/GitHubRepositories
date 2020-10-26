package com.alexeymerov.githubrepositories.app.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

@Module
@InstallIn(ApplicationComponent::class)
object BaseApiModule {

	@Provides
	fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()
			.connectionPool(ConnectionPool(5, 30, SECONDS))
			.apply {
				val httpLoggingInterceptor = HttpLoggingInterceptor()
				httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
				addInterceptor(httpLoggingInterceptor)
				addNetworkInterceptor(StethoInterceptor())
			}

	@Provides
	fun provideOkHttpClient(clientBuilder: OkHttpClient.Builder): OkHttpClient = clientBuilder.build()

	@Provides
	fun provideJsonConverter(): Converter.Factory = MoshiConverterFactory.create()

	@Provides
	fun provideCallAdapterFactory(): CallAdapter.Factory = CoroutineCallAdapterFactory()

	@Provides
	fun provideRetrofitBuilder(client: OkHttpClient,
							   converterFactory: Converter.Factory,
							   callAdapterFactory: CallAdapter.Factory): Retrofit.Builder = Retrofit.Builder()
			.client(client)
			.addConverterFactory(converterFactory)
			.addCallAdapterFactory(callAdapterFactory)

	@Provides
	fun provideRetrofit(retrofitBuilder: Retrofit.Builder, serverUrl: String): Retrofit =
		retrofitBuilder.baseUrl(serverUrl).build()

}