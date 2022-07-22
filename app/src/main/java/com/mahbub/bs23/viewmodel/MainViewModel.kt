package com.mahbub.bs23.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahbub.bs23.model.Item
import com.mahbub.bs23.repo.Repository
import com.mahbub.bs23.repo.TAG
import com.mahbub.bs23.utils.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repo: Repository
): ViewModel() {

// ============ LIVE DATA Example <===================
    private val _repositoriesMutableLiveData: MutableLiveData<ResponseHandler<List<Item>>> =  MutableLiveData()
    val observer: LiveData<ResponseHandler<List<Item>>> = _repositoriesMutableLiveData

    fun getTop50Repositories() {
        repo.getTop50Repositories(_repositoriesMutableLiveData)
        Timber.tag("MAIN_VM").d("NETWORK CALL DONE")
    }

// ============ Shared Flow  Example <===================
    private val _repoFlow = MutableSharedFlow<ResponseHandler<List<Item>>>()
    val stateFlow = _repoFlow.asSharedFlow()
    fun getTop50RepositoriesFlow() {
       viewModelScope.launch{
          repo.getTop50Repositories()
              .catch {
                  emit(ResponseHandler.Error("${it.localizedMessage}"))
              }
              .flowOn(Dispatchers.IO)
              .collectLatest {
              _repoFlow.emit(it )
                  Timber.tag("FLOW_").d("ViewModel:  ${Thread.currentThread().name}")
          }
       }
        Timber.tag("MAIN_VM").d("NETWORK CALL DONE")
    }


    override fun onCleared() {
        super.onCleared()
        repo.clearJobs()
    }
}