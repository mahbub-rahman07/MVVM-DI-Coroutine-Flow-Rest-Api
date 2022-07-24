package com.mahbub.rest_mvvm.viewmodel

import androidx.lifecycle.*
import com.mahbub.rest_mvvm.model.Item
import com.mahbub.rest_mvvm.repo.Repository
import com.mahbub.rest_mvvm.utils.ResponseHandler
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

//    ========> using shared view model concept passing value through flow <========
    private val _itemDetails = MutableLiveData<Item>()
    val itemDetails = _itemDetails.asFlow()

    fun setCurrentItem(item: Item) {
        _itemDetails.postValue(item)
    }



    override fun onCleared() {
        super.onCleared()
        repo.clearJobs()
    }
}