package com.mahbub.rest_mvvm.repo

import androidx.lifecycle.MutableLiveData
import com.mahbub.rest_mvvm.model.Item
import com.mahbub.rest_mvvm.utils.ResponseHandler
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

const val TAG = "REPO"

@Singleton
class Repository @Inject constructor(
    private val service: ApiService,
    private val cacheDB: CacheDB
) {
    private val jobs: ArrayList<CompletableJob> = arrayListOf()


    fun getTop50Repositories(observer: MutableLiveData<ResponseHandler<List<Item>>>) {

        observer.postValue(ResponseHandler.Loading())

        val query = mapOf(
            "q" to "android",
            "page" to "1",
            "per_page" to "50",
            "sort" to "stargazers_count"
        )

//      Fetching data from cache first  then continue api call
        val cache = cacheDB.getItems()
        if (cache.isNotEmpty()) {
            observer.postValue(ResponseHandler.Success(cache))
        }

        val job: CompletableJob = Job()
        jobs.add(job)

        val handler = CoroutineExceptionHandler { _, exception ->
            Timber.tag(TAG).e(exception)
//            if already has in cache do not need to show exception error
            if (cache.isEmpty()) {
                observer.postValue(
                    ResponseHandler.Error(
                        msg = exception.message ?: "Unknown error!"
                    )
                )
            }
        }

        CoroutineScope(Dispatchers.IO + job + handler).launch {

            val response = service.getTop50Result(query)
            if (response.isSuccessful) {

                response.body().let {
                    if (it != null) {
//                      using single source of truth view will update only if cache is empty
                        if (cache.isEmpty()) {
                            observer.postValue(ResponseHandler.Success(it.items))
                        }
//                      save the response for offline use
                        cacheDB.saveItems(it.items)

                        Timber.tag(TAG).d("Success: item count -> ${it.items.size}")
                    } else {
                        observer.postValue(ResponseHandler.Error("No data found!"))

                        Timber.tag(TAG).d("Empty: no repository found")
                    }
                }


            } else {
                observer.postValue(ResponseHandler.Error(response.raw().message))
                Timber.tag(TAG).d("Error: ${response.raw().message}")
            }

            job.complete()
        }

    }

    fun getTop50Repositories(): Flow<ResponseHandler<List<Item>>> = flow {

        val query = mapOf(
            "q" to "android",
            "page" to "1",
            "per_page" to "50",
            "sort" to "stargazers_count"
        )

//      Fetching data from cache first  then continue api call
        val cache = cacheDB.getItems()
        if (cache.isNotEmpty()) {
            emit(ResponseHandler.Success(cache))
        }
        Timber.tag("FLOW_").d("getTop50Repositories:  ${Thread.currentThread().name}")
        val response = service.getTop50Result(query)
        if (response.isSuccessful) {

            response.body().let {
                if (it != null) {
//                  using single source of truth view will update only if cache is empty
                    if (cache.isEmpty()) {
                        emit(ResponseHandler.Success(it.items))
                    }
//                  save the response for offline use
                    cacheDB.saveItems(it.items)

                    Timber.tag("FLOW_").d("Success: item count -> ${it.items.size} ${Thread.currentThread().name}")
                } else {
                    emit(ResponseHandler.Error("No data found!"))
                    Timber.tag(TAG).d("Empty: no repository data found")
                }
            }

        }else {
            emit(ResponseHandler.Error("Api call failed"))
        }

    }

    fun clearJobs() {
        jobs.forEach { it.cancel() }
    }

}