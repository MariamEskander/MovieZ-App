package app.telda.task.data.remote.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page") var page: Int,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("total_results") var totalResults: Int,
    @SerializedName("results") var results: ArrayList<Movie>
)


@Entity
data class Movie(
    @field:SerializedName("id") @PrimaryKey var id: String,
    @field:SerializedName("title") var title: String,
    @field:SerializedName("overview") var overview: String,
    @field:SerializedName("backdrop_path") var backdropPath: String?,
    @field:SerializedName("poster_path") var posterPath: String?,
    @field:SerializedName("release_date") var releaseDate: String,
    @field:SerializedName("tagline") var tagline: String? = "",
    @field:SerializedName("revenue") var revenue: Int? = 0,
    @field:SerializedName("status") var status: String? = "",
    @field:SerializedName("isFavorite") var isFavorite: Boolean? = false
)