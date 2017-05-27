package com.example.oldroy.ssebook.models

import retrofit2.Call
import retrofit2.http.GET

interface BookApi {
    @GET("books")
    fun getAllBooks(): Call<List<Book>>
}
