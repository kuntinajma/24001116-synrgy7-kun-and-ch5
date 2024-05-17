package com.listing.movie.network

import com.listing.movie.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getNowPlaying(@Query("api_key") apiKey: String?): Call<MovieResponse>

    @GET("search/movie")
    fun getSearch(
        @Query("api_key") apiKey: String?,
        @Query("query") query: String?
    ): Call<MovieResponse>
}