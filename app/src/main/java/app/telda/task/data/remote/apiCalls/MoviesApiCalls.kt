package app.telda.task.data.remote.apiCalls


import app.telda.task.BuildConfig
import app.telda.task.data.remote.apis.MoviesApis


class MoviesApiCalls(private val apis: MoviesApis) {
    suspend fun getPopularMovies(page: Int) = apis.getPopularMovies(BuildConfig.apiKey, page)
    suspend fun search(page: Int, query: String, year: Int?) =
        apis.search(BuildConfig.apiKey, page, query, year)

    suspend fun getMovieDetails(id: String) = apis.getMovieDetails(id, BuildConfig.apiKey)
    suspend fun getSimilarMovies(id: String) = apis.getSimilarMovies(id, BuildConfig.apiKey, 1)
    suspend fun getMovieCredits(id: String) = apis.getMovieCredits(id, BuildConfig.apiKey)

}