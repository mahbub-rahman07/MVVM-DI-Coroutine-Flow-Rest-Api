package com.tenminuteschool.bs23.repo

import com.tenminuteschool.bs23.model.TopRepositoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("search/repositories?q=android&page=1,&per_page=1&sort=stargazers_count")
    suspend fun getTop50Result() : Response<TopRepositoryResponse>
}