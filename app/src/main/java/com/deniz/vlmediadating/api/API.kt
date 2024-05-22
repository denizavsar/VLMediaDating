package com.deniz.vlmediadating.api

import com.deniz.vlmediadating.api.response.UsersResponse
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val apiService: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ApiService::class.java)

    suspend fun getUsers(page: Int): UsersResponse {
        return apiService.getCharacters(page)
    }
}