package com.mahbub.bs23.repo

import androidx.lifecycle.MutableLiveData
import com.mahbub.bs23.model.Item
import com.mahbub.bs23.utils.ResponseHandler
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

const val TAG = "REPO"

@Singleton
class Repository @Inject constructor(private val service: ApiService) {
    private val jobs: ArrayList<CompletableJob> = arrayListOf()


    fun getTop50Repositories(observer: MutableLiveData<ResponseHandler<List<Item>>>) {

        observer.postValue(ResponseHandler.Loading())

        val query = mapOf(
            "q" to "android",
            "page" to "1",
            "per_page" to "50",
            "sort" to "stargazers_count"
        )

        val job: CompletableJob = Job()
        jobs.add(job)

        val handler = CoroutineExceptionHandler { _, exception ->
            Timber.tag(TAG).e(exception)
            observer.postValue(
                ResponseHandler.Error(
                    msg = exception.message ?: "Unknown error!"
                )
            )
        }

        CoroutineScope(Dispatchers.IO + job + handler).launch {

            val  response  = service.getTop50Result(query)
            if(response.isSuccessful) {

                response.body().let {
                    if (it != null) {
                        observer.postValue(ResponseHandler.Success(it.items))
                        Timber.tag(TAG).d("Success: item count -> ${it.items.size}")
                    }else {
                        observer.postValue(ResponseHandler.Error("No data found!"))
                        Timber.tag(TAG).d("Empty: no repository found")
                    }
                }


            }else {
                observer.postValue(ResponseHandler.Error(response.raw().message))
                Timber.tag(TAG).d("Error: ${response.raw().message}")
            }

            job.complete()
        }

    }


    fun clearJobs() {
        jobs.forEach { it.cancel()}
    }

}