package com.listing.movie.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object{
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        const val BASE_ORIGINAL_IMAGE_URL = "https://image.tmdb.org/t/p/original"
        const val API_KEY = "204fa6042d4500b60b61c07898ab34eb"
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var apiServiceInstance: ApiService = retrofit.create(ApiService::class.java)
    }
}