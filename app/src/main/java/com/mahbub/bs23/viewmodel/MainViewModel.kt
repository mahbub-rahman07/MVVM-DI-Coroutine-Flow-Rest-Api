package com.mahbub.bs23.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahbub.bs23.model.Item
import com.mahbub.bs23.repo.Repository
import com.mahbub.bs23.utils.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
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

// ============ SHared Flow  Example <===================
    private val _repoFlow:MutableStateFlow<ResponseHandler<List<Item>>> = MutableStateFlow(ResponseHandler.Empty())
    val stateFlow: StateFlow<ResponseHandler<List<Item>>> = _repoFlow.asStateFlow()
    fun getTop50RepositoriesFlow() {
       viewModelScope.launch{
          repo.getTop50Repositories()
              .catch {
                  emit(ResponseHandler.Error("${it.localizedMessage}"))
              }
              .collectLatest {
              _repoFlow.emit(it )
          }
       }
        Timber.tag("MAIN_VM").d("NETWORK CALL DONE")
    }


    override fun onCleared() {
        super.onCleared()
        repo.clearJobs()
    }
}