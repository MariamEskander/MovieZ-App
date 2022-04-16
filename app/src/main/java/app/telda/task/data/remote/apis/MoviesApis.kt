package app.telda.task.data.remote.apis

import app.telda.task.data.remote.entities.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApis {

    @GET(SUB_URL_MOVIES +URL_GET_POPULAR)
    suspend fun getPopularMovies(
        @Query(API_KEY)  apiKey: String,
        @Query(PAGE)  page: Int,
    ): Response<MoviesResponse>

    @GET(URL_SEARCH)
    suspend fun search(
        @Query(API_KEY)  apiKey: String,
        @Query(PAGE)  page: Int,
        @Query(QUERY)  query: String,
        @Query(YEAR)  year: Int?,
    ): Response<MoviesResponse>


    companion object {
        const val API_KEY = "api_key"
        const val PAGE = "page"
        const val QUERY = "query"
        const val YEAR = "year"

        const val SUB_URL_MOVIES = "movie/"
        const val URL_GET_POPULAR = "popular"
        const val URL_GET_DETAILS = "get-movie-details"
        const val URL_GET_SIMILAR = "get-similar-movies"
        const val URL_GET_MOVIE_CREDITS = "get-movie-credits"

        const val URL_SEARCH = "search/movie"
    }
}