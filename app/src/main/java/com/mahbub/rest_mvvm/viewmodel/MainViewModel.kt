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
) : ViewModel() {

    // ============ State Flow  Example with lazy initialization <===================
    private val _repoFlow: MutableStateFlow<ResponseHandler<List<Item>>> by lazy {
        getTop50Repositories()
        return@lazy MutableStateFlow<ResponseHandler<List<Item>>>(ResponseHandler.Empty())

    }
    val stateFlow = _repoFlow.asStateFlow()

    private fun getTop50Repositories() {
        viewModelScope.launch {
            repo.getTop50Repositories()
                .catch {
                    emit(ResponseHandler.Error("${it.localizedMessage}"))
                }
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    _repoFlow.emit(it)
                    Timber.tag("FLOW_").d("ViewModel:  ${Thread.currentThread().name}")
                }
        }
        Timber.tag("MAIN_VM").d("NETWORK CALL DONE")
    }

    //========> using shared view model concept passing value through flow <========
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