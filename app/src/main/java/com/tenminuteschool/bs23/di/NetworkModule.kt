package com.tenminuteschool.bs23.di

import androidx.viewbinding.BuildConfig
import com.tenminuteschool.bs23.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofitClient(client: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URL)
            .build()

    }

    @Singleton
    @Provides
    fun providesClient(interceptor: Interceptor): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        clientBuilder.readTimeout(10, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(10, TimeUnit.SECONDS)
        clientBuilder.addInterceptor(interceptor)
        return clientBuilder.build()
    }
    @Singleton
    @Provides
    fun providesInterceptor(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor { s ->
            Timber.tag("NETWORK_CALL").d("response: $s")
        }
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        return httpLoggingInterceptor
    }
}