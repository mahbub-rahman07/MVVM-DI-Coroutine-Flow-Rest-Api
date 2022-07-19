package com.mahbub.bs23.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mahbub.bs23.app.App
import com.mahbub.bs23.app.ObjectBox
import com.mahbub.bs23.model.Item
import com.mahbub.bs23.repo.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.objectbox.Box
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }
    @Singleton
    @Provides
    fun providesBoxData(): Box<Item> {
        return ObjectBox.store.boxFor(Item::class.java)
    }


}