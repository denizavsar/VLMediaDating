package com.deniz.vlmediadating.api

import com.deniz.vlmediadating.api.response.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): UsersResponse
}