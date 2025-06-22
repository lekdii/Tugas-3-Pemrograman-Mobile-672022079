package com.diva.tugas3

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("products")
    suspend fun getProducts(): ProductResponse

    @GET("users/{id}")
    suspend fun getUsers(@Path("id") id: Int): User

}