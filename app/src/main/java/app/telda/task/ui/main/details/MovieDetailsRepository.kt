package app.telda.task.ui.main.details


import app.telda.task.base.BaseRepository
import app.telda.task.data.local.database.MoviesDao
import app.telda.task.data.remote.apiCalls.MoviesApiCalls
import app.telda.task.data.remote.entities.Movie
import retrofit2.Response
import javax.inject.Inject


class MovieDetailsRepository @Inject constructor(
    private val apiCalls: MoviesApiCalls,
    private val moviesDao: MoviesDao
) : BaseRepository() {


    suspend fun getMovieDetails(id: String) = apiCalls.getMovieDetails(id)
    suspend fun getSimilarMovies(id: String) = apiCalls.getSimilarMovies(id)
    suspend fun getMovieCredits(id: String) = apiCalls.getMovieCredits(id)

    fun saveMovie(movie: Movie): Response<Boolean> {
        moviesDao.insertMovie(movie)
        return Response.success(true)
    }

    fun isFavorite(id: String): Response<Movie?> {
        return Response.success(moviesDao.getMovieById(id))
    }

    fun deleteMovie(id: String): Response<Boolean> {
        moviesDao.deleteMovieById(id)
        return Response.success(true)
    }
}