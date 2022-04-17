package app.telda.task.data.remote.apis

import app.telda.task.data.remote.entities.CreditResponse
import app.telda.task.data.remote.entities.Movie
import app.telda.task.data.remote.entities.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET(SUB_URL_MOVIES +URL_GET_DETAILS)
    suspend fun getMovieDetails(
        @Path(ID)  id: String,
        @Query(API_KEY)  apiKey: String,
    ): Response<Movie>

    @GET(SUB_URL_MOVIES +URL_GET_SIMILAR)
    suspend fun getSimilarMovies(
        @Path(ID)  id: String,
        @Query(API_KEY)  apiKey: String,
        @Query(PAGE)  page: Int,
    ): Response<MoviesResponse>

    @GET(SUB_URL_MOVIES +URL_GET_MOVIE_CREDITS)
    suspend fun getMovieCredits(
        @Path(ID)  id: String,
        @Query(API_KEY)  apiKey: String
    ): Response<CreditResponse>



    companion object {
        const val API_KEY = "api_key"
        const val PAGE = "page"
        const val QUERY = "query"
        const val YEAR = "year"
        const val ID = "movie_id"

        const val SUB_URL_MOVIES = "movie/"
        const val URL_GET_POPULAR = "popular"
        const val URL_GET_DETAILS = "{movie_id}"
        const val URL_GET_SIMILAR = "{movie_id}/similar"
        const val URL_GET_MOVIE_CREDITS = "{movie_id}/credits"

        const val URL_SEARCH = "search/movie"
    }
}