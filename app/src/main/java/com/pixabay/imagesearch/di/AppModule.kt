package com.pixabay.imagesearch.di

import android.content.Context
import com.pixabay.imagesearch.data.remote.ApiService
import com.pixabay.imagesearch.BuildConfig
import com.pixabay.imagesearch.data.repository.NetworkDataSource
import com.pixabay.imagesearch.GlobalApplication
import com.pixabay.imagesearch.ui.commons.Network
import com.pixabay.imagesearch.ui.commons.NetworkConnectivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): GlobalApplication =
        app as GlobalApplication

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext app: Context): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideRetrofit(): ApiService = provideRetrofitApi()

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity =
        Network(context)

    @Singleton
    @Provides
    fun providesNetworkDataSource(apiService: ApiService): NetworkDataSource =
        NetworkDataSource(apiService)

    private fun provideRetrofitApi(
        baseUrl: HttpUrl = BuildConfig.BASE_URL.toHttpUrl(),
        client: () -> OkHttpClient = { makeOkHttpClient() },
    ): ApiService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client())
            .build().create(ApiService::class.java)


    private fun makeOkHttpClient(
        logging: () -> Interceptor = { loggingInterceptor() },
        authorization: () -> Interceptor = { authorizationInterceptor() },
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logging())
            .addInterceptor(authorization())
            .build()

    private fun authorizationInterceptor() = Interceptor {
        val url: HttpUrl = it.request().url
            .newBuilder()
            .addQueryParameter("key", BuildConfig.API_KEY)
            .build()
        val request: Request = it.request().newBuilder().url(url).build()
        it.proceed(request)
    }

    private fun loggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }

}