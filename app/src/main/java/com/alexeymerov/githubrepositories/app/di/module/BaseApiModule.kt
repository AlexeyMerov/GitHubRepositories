package com.alexeymerov.githubrepositories.app.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

@Module
class BaseApiModule {

	@Provides
	fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()
		.connectionPool(ConnectionPool(5, 30, SECONDS))
		.connectTimeout(30, SECONDS)
		.readTimeout(30, SECONDS)
		.writeTimeout(30, SECONDS)
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
	fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

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