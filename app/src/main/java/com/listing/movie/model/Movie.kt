package com.listing.movie.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

@kotlinx.serialization.Serializable
data class Movie (
    @SerializedName("id")
    var id: Int,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("vote_average")
    var vote: Double,
    @SerializedName("title")
    var title: String
) : Serializable {
    val stringVote: String
        get() = "$vote/10"
}
