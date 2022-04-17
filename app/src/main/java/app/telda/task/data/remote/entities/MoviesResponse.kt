package app.telda.task.data.remote.entities


import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page") var page: Int,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("total_results") var totalResults: Int,
    @SerializedName("results") var results: ArrayList<Movie>
)



data class Movie(
    @SerializedName("id") var id: String,
    @SerializedName("title") var title: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("backdrop_path") var backdropPath: String?,
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("release_date") var releaseDate: String,
    @SerializedName("tagline") var tagline: String?="",
    @SerializedName("revenue") var revenue: Double?=0.0,
    @SerializedName("status") var status: String?=""
)