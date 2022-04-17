package app.telda.task.ui.main.wishlist


import app.telda.task.base.BaseRepository
import app.telda.task.data.local.database.MoviesDao
import app.telda.task.data.remote.entities.Movie
import retrofit2.Response
import javax.inject.Inject


class WishlistRepository @Inject constructor(private val moviesDao: MoviesDao) : BaseRepository() {

    fun getMovies(): Response<List<Movie>?> {
        return Response.success(moviesDao.getMovies())
    }

    fun deleteMovie(id: String): Response<Boolean> {
        moviesDao.deleteMovieById(id)
        return Response.success(true)
    }
}