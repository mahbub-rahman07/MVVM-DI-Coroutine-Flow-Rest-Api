package com.mahbub.bs23.di

import com.mahbub.bs23.view.adapter.ListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @FragmentScoped
    @Provides
    fun providesListAdapter(): ListAdapter {
        return ListAdapter(arrayListOf())
    }


}