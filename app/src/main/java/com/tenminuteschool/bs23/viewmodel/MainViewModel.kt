package com.tenminuteschool.bs23.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tenminuteschool.bs23.model.Item
import com.tenminuteschool.bs23.repo.Repository
import com.tenminuteschool.bs23.utils.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repo: Repository
): ViewModel() {


    private val _repositoriesMutableLiveData: MutableLiveData<ResponseHandler<List<Item>>> =  MutableLiveData()
    val observer: LiveData<ResponseHandler<List<Item>>> = _repositoriesMutableLiveData

    fun getTop50Repositories() {
        repo.getTop50Repositories(_repositoriesMutableLiveData)
        Timber.tag("MAIN_VM").d("NETWORK CALL DONE")
    }


    override fun onCleared() {
        super.onCleared()
        repo.clearJobs()
    }
}