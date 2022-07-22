package com.mahbub.rest_mvvm.repo

import com.mahbub.rest_mvvm.model.TopRepositoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
//    ?q=android&page=1,&per_page=1&sort=stargazers_count
    @GET("search/repositories")
    suspend fun getTop50Result(@QueryMap map: Map<String, String>): Response<TopRepositoryResponse>
}